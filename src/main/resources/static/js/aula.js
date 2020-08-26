 tinymce.init({
  selector: "#conteudo",
  height: "92%",
  skin: "borderless",
  branding: false,
  resize: false,
  readonly : 1,
  menubar: false,
  toolbar: false
});

(function () {
  // -PANELS RESIZING-
  // Gutter
  var gutter; // Element
  var offset; // Where the mouse is holding the gutter
  
  var panelClosed; // Boolean value
  var preClick; // Gutter position pre-maximize

  // -THREE.JS-
  // Panel
  var container; // Element
  
  // Essentials
  var scene;
  var camera;
  var renderer;
  
  // Controls
  var orbit; // Orbit Controls (Camera)
  
  // Scene Meshes
  var planes = [];
  
  // Other three.js related variables
  var animation; // AnimationFrame id, use for starting / stopping the rendering

  init();
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

    // -THREE.JS-
    container = document.getElementById("container");

    scene = new THREE.Scene();
    scene.background = new THREE.Color(0x131c26);

    camera = new THREE.PerspectiveCamera(80, 1, 0.1, 100);
    camera.aspect = container.offsetWidth / container.offsetHeight;
    camera.updateProjectionMatrix();
    camera.position.y = 8;
    camera.position.z = 8;

    renderer = new THREE.WebGLRenderer({
      canvas: document.getElementById("three"),
      antialias: true
    });
    renderer.setPixelRatio(window.devicePixelRatio);
    renderer.setSize(container.offsetWidth, container.offsetHeight);

    orbit = new OrbitControls(camera, renderer.domElement);
    orbit.screenSpacePanning = false;
    orbit.minDistance = 1;
    orbit.maxDistance = 30;
    
    planes = initPlanes();
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
  
  function initPlanes() {
    var grid = [];
    var planes = [];

    planes.push(new THREE.Mesh(
    	      new THREE.PlaneBufferGeometry(20, 20),
    	      new THREE.MeshBasicMaterial({
    	        color: 0x333333,
    	        side: THREE.DoubleSide,
    	        transparent: true,
    	        opacity: 0.3
    	    })));
    grid.push(new THREE.GridHelper(20, 20, 0x0000ff));

    planes[0].rotation.x = Math.PI / -2;

    scene.add(planes[0]);
    scene.add(grid[0]);

    return planes;
  }

  function canvasToContainerSize() {
    camera.aspect = container.offsetWidth / container.offsetHeight;
    camera.updateProjectionMatrix();
    renderer.setSize(container.offsetWidth, container.offsetHeight);
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