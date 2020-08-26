(function () {
  // -PANELS-
  var gutter;
  var offset;
  var panelClosed;
  var preClick;

  // -THREE.JS-
  var container;
  var scene;
  var camera;
  var renderer;
  var controls;
  var transform;
  var mouse;
  var animation;
  var planes;
  var vertices;

  init();
  planes = grids();
  maxRight();
  start();

  function init() {
    // -PANELS RESIZING-  
    offset = 0;
    panelClosed = false;

    gutter = document.querySelector(".vertical-gutter");
    gutter.addEventListener("mousedown", startResize, false);
    gutter.addEventListener("dblclick", centralize, false);
    
    // Gutter position before max
    preClick = (gutter.offsetLeft + 2.5) / window.innerWidth * 100;

    // Maximize button - left
    document.querySelector(".max.left").addEventListener("click", maxRight, false);

    // Maximize button - right
    document.querySelector(".max.right").addEventListener("click", maxLeft, false);


    // -THREE.JS CANVAS-
    container = document.getElementById("container");

    scene = new THREE.Scene();
    scene.background = new THREE.Color(0x131c26);

    camera = new THREE.PerspectiveCamera(80, 1, 0.1, 100);
    camera.aspect = container.offsetWidth / container.offsetHeight;
    camera.updateProjectionMatrix();
    camera.position.z = 10;
    camera.position.x = 10;

    renderer = new THREE.WebGLRenderer({
      canvas: document.getElementById("three"),
      antialias: true
    });
    renderer.setPixelRatio(window.devicePixelRatio);
    renderer.setSize(container.offsetWidth, container.offsetHeight);

    controls = new OrbitControls(camera, renderer.domElement);
    controls.screenSpacePanning = false;
    controls.minDistance = 1;
    controls.maxDistance = 30;

    transform = new TransformControls(camera, renderer.domElement);
    transform.addEventListener("dragging-changed", (event) => {
      controls.enabled = !event.value;
    });
    
    scene.add(transform);

    mouse = new THREE.Vector2();

    raycaster = new THREE.Raycaster();

    vertices = [];

    toolbar();

    container.addEventListener("mousemove", () => {
      var rect = renderer.domElement.getBoundingClientRect();
      mouse.x = ((event.clientX - rect.left) / rect.width) * 2 - 1;
      mouse.y = - ((event.clientY - rect.top) / rect.height) * 2 + 1;
      raycaster.setFromCamera(mouse, camera);
    }, false);
  }

  function toolbar() {
    var moveTool = document.getElementById("move");
    var pointTool = document.getElementById("point");
    var lineTool = document.getElementById("line");

    moveTool.addEventListener("click", move, false);
    pointTool.addEventListener("click", point, false);
    lineTool.addEventListener("click", line, false);

    var lastButton = moveTool;
    var hoverMesh = new THREE.Mesh(
      new THREE.SphereBufferGeometry(0.1, 20, 20),
      new THREE.MeshBasicMaterial({ color: 0x0000ff })
    );
    var lastMesh;
    var selected;

    moveTool.click();

    // CHANGE TOOLS
    function click(button) {
      lastButton.disabled = false;
      button.disabled = true;
      lastButton = button;
      removeListeners();
    }
    
    function swapTransformAxis() {
      transform.showX = !transform.showX;
      transform.showY = !transform.showY;
      transform.showZ = !transform.showZ;
    }
    
    function setTransformAxis(horizontal) {
      if(horizontal) {
        transform.showX = true;
        transform.showY = false;
        transform.showZ = true;
      } else {
        transform.showX = true;
        transform.showY = false;
        transform.showZ = true;
      }
    }

    function removeListeners() {
      removePoint();
      removeLine();
    }

    // MOVE TOOL
    function move() {
      click(moveTool);
    }

    // POINT TOOL
    function point() {
      click(pointTool);
  
      transform.showX = false;
      transform.showZ = false;

      container.addEventListener("mousemove", pointMove, false);
      container.addEventListener("mousedown", pointDown, false);
      container.addEventListener("keydown", pointKey, false);
    }

    var pointMove = function () {
      var intersects = raycaster.intersectObjects(vertices);
      
      if(intersects.length > 0 || transform.dragging) {
        scene.remove(hoverMesh);
        if(!transform.dragging) {
          transform.attach(intersects[0].object);
          
          if(lastMesh != null) {
            lastMesh.material.color.setHex(0xff0000);
          } 
          
          lastMesh = intersects[0].object;
          lastMesh.material.color.setHex(0xffff00);
        }
        return;
      }
      
      var intersects = raycaster.intersectObjects(planes);
      
      if (intersects.length > 0) {
        scene.add(hoverMesh);
        hoverMesh.position.copy(intersects[0].point);
      } else {
        scene.remove(hoverMesh);
      }
    }

    var pointDown = function (event) {
      if(event.button !== 0) {
        return;
      }
      
      var intersects = raycaster.intersectObjects(vertices);
      
      if(intersects.length > 0 && !transform.dragging) {
        swapTransformAxis();
        return;
      }
      
      intersects = raycaster.intersectObjects(planes);
      
      if(intersects.length > 0 && !transform.dragging) {
        let point = hoverMesh.clone();
        point.material = hoverMesh.material.clone();
        point.material.color.setHex(0xffff00);
        scene.add(point);
        vertices.push(point);
        transform.attach(point);
        
        if(lastMesh != null) {
          lastMesh.material.color.setHex(0xff0000);
        }        
        lastMesh = point;
      } else if(!transform.dragging) {
        if(lastMesh != null) {
          lastMesh.material.color.setHex(0xff0000);
        }
        lastMesh = null;
        transform.detach();
      }
    }
    
    var pointKey = function (event) {
      if((event.key === "Backspace" || event.key === "Delete" || event.key ==="Del") && lastMesh != null) {
        transform.detach();
        scene.remove(lastMesh);
        
        let i = vertices.indexOf(lastMesh);
        if(i > -1) {
          vertices.splice(i, 1);
        }
        lastMesh = null;
      }
    }

    function removePoint() {
      transform.detach();
      scene.remove(hoverMesh);
      container.removeEventListener("mousemove", pointMove, false);
      container.removeEventListener("mousedown", pointDown, false);
      container.removeEventListener("keydown", pointKey, false);
    }

    // LINE TOOL
    function line() {
      click(lineTool);

      container.addEventListener("mousemove", lineMove, false);
      container.addEventListener("mousedown", lineDown, false);
      container.addEventListener("mouseup", lineUp, false);
    }

    var lineMove = function () {
      var intersects = raycaster.intersectObjects(vertices);
      
      if(intersects.length > 0) {
        if(lastMesh != null) {
          lastMesh.material.color.setHex(0xff0000);
        }  
        lastMesh = intersects[0].object;
        lastMesh.material.color.setHex(0xffff00);
      }
    }

    var lineDown = function () {
      var intersects = raycaster.intersectObjects(vertices);
      
      if(intersects.length > 0 ) {
        intersects[0].object.position;  
      }
    }

    var lineUp = function () {

    }

    function removeLine() {
      container.removeEventListener("mousemove", lineMove, false);
      container.removeEventListener("mousedown", lineDown, false);
      container.removeEventListener("mouseup", lineUp, false);
    }
  }

  function startResize(e) {
    offset = gutter.offsetLeft - e.clientX;
    window.addEventListener('mousemove', resize, false);
    window.addEventListener('mouseup', stopResize, false);
  }

  function resize(e) {
    var percentageSize = (e.clientX + offset) / window.innerWidth * 100;
    if (percentageSize > 85) {
      if (percentageSize > 95) {
        panelClosed = true;
        document.body.style.gridTemplateColumns = "1fr 5px 0"
      } else {
        panelClosed = false;
        preClick = (gutter.offsetLeft + 2.5) / window.innerWidth * 100;
        document.body.style.gridTemplateColumns = "calc(85% - 5px) 5px minmax(0, 1fr)"
      }
    } else if (percentageSize < 15) {
      if (percentageSize < 5) {
        panelClosed = true;
        document.body.style.gridTemplateColumns = "0 5px minmax(0, 1fr)"
      } else {
        panelClosed = false;
        preClick = (gutter.offsetLeft + 2.5) / window.innerWidth * 100;
        document.body.style.gridTemplateColumns = "calc(15% - 5px) 5px minmax(0, 1fr)"
      }
    } else {
      panelClosed = false;
      document.body.style.gridTemplateColumns = percentageSize + "% 5px minmax(0, 1fr)";
    }
    canvasToContainerSize();
  }

  function stopResize(e) {
    window.removeEventListener('mousemove', resize, false);
    window.removeEventListener('mouseup', stopResize, false);
  }

  function maxRight() {
    if (panelClosed) {
      document.body.style.gridTemplateColumns = preClick + "% 5px minmax(0, 1fr)";
    } else {
      preClick = (gutter.offsetLeft + 2.5) / window.innerWidth * 100;
      document.body.style.gridTemplateColumns = "0 5px minmax(0, 1fr)";
    }
    panelClosed = !panelClosed;
    canvasToContainerSize();
  }
  
  function centralize() {
    document.body.removeAttribute("style");
    canvasToContainerSize();
  }

  function maxLeft() {
    if (panelClosed) {
      document.body.style.gridTemplateColumns = preClick + "% 5px minmax(0, 1fr)";
    } else {
      preClick = (gutter.offsetLeft + 2.5) / window.innerWidth * 100;
      document.body.style.gridTemplateColumns = "1fr 5px 0";
    }
    panelClosed = !panelClosed;
    canvasToContainerSize();
  }

  function canvasToContainerSize() {
    camera.aspect = container.offsetWidth / container.offsetHeight;
    camera.updateProjectionMatrix();
    renderer.setSize(container.offsetWidth, container.offsetHeight);
  }

  function grids() {
    var grid = [];
    var planes = [];

    planes.push(new THREE.Mesh(
      new THREE.PlaneBufferGeometry(10, 10),
      new THREE.MeshBasicMaterial({
        color: 0x888888,
        side: THREE.DoubleSide,
        transparent: true,
        opacity: 0.3
    })));
    grid.push(new THREE.GridHelper(10, 10, 0x0000ff));

    scene.add(planes[0]);
    scene.add(grid[0]);

    planes[0].rotation.x = Math.PI / -2;

    return planes;
  }

  function render() {
    renderer.render(scene, camera);

    requestAnimationFrame(render);
  }

  function start() {
    animation = requestAnimationFrame(render);
  }

  function stop() {
    cancelAnimationFrame(animation);
  }
})();