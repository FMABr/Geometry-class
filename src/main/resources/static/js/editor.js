
tinymce.init({
  selector: '.conteudo',
  plugins: 'print preview paste importcss searchreplace autolink autosave save directionality code visualblocks visualchars fullscreen image link media template codesample table charmap hr pagebreak nonbreaking anchor toc insertdatetime advlist lists wordcount imagetools textpattern noneditable help charmap quickbars emoticons',
  imagetools_cors_hosts: ['picsum.photos'],
  menubar: 'edit view insert format tools table help',
  toolbar: 'undo redo | bold italic underline strikethrough | fontsizeselect formatselect | alignleft aligncenter alignright alignjustify | outdent indent |  numlist bullist | forecolor backcolor removeformat | pagebreak | charmap emoticons | fullscreen  preview save print | insertfile image media template link anchor codesample | ltr rtl',
  toolbar_sticky: true,
  autosave_ask_before_unload: true,
  autosave_interval: "30s",
  autosave_prefix: "{path}{query}-{id}-",
  autosave_restore_when_empty: false,
  autosave_retention: "2m",
  image_advtab: true,
  content_css: '//www.tiny.cloud/css/codepen.min.css',
  image_list: [
    { title: 'My page 1', value: 'http://www.tinymce.com' },
    { title: 'My page 2', value: 'http://www.moxiecode.com' }
  ],
  image_class_list: [
    { title: 'None', value: '' },
    { title: 'Some class', value: 'class-name' }
  ],
  importcss_append: true,
  file_picker_callback: function (callback, value, meta) {
    /* Provide file and text for the link dialog */
    if (meta.filetype === 'file') {
      callback('https://www.google.com/logos/google.jpg', { text: 'My text' });
    }

    /* Provide image and alt text for the image dialog */
    if (meta.filetype === 'image') {
      callback('https://www.google.com/logos/google.jpg', { alt: 'My alt text' });
    }

    /* Provide alternative source and posted for the media dialog */
    if (meta.filetype === 'media') {
      callback('movie.mp4', { source2: 'alt.ogg', poster: 'https://www.google.com/logos/google.jpg' });
    }
  },
  templates: [
        { title: 'New Table', description: 'creates a new table', content: '<div class="mceTmpl"><table width="98%%"  border="0" cellspacing="0" cellpadding="0"><tr><th scope="col"> </th><th scope="col"> </th></tr><tr><td> </td><td> </td></tr></table></div>' },
    { title: 'Starting my story', description: 'A cure for writers block', content: 'Once upon a time...' },
    { title: 'New list with dates', description: 'New List with dates', content: '<div class="mceTmpl"><span class="cdate">cdate</span><br /><span class="mdate">mdate</span><h2>My List</h2><ul><li></li><li></li></ul></div>' }
  ],
  template_cdate_format: '[Date Created (CDATE): %m/%d/%Y : %H:%M:%S]',
  template_mdate_format: '[Date Modified (MDATE): %m/%d/%Y : %H:%M:%S]',
  skin: 'oxide-dark',
  language: "pt_BR",
  resize: false,
  width: "100%",
  height: "92%",
  image_caption: true,
  quickbars_selection_toolbar: 'bold italic | quicklink h2 h3 blockquote quickimage quicktable',
  noneditable_noneditable_class: "mceNonEditable",
  toolbar_mode: 'sliding',
  contextmenu: "link image imagetools table"
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
  //var drag; // Drag Controls (Meshes -> vertices)
  var transform; // Transform Controls (Meshes - > Geometries)
  
  // Reusable general meshes
  var hoverMesh;
  var planeMesh;
  var grid;
  
  // Scene Meshes
  var planes = [];
  var grids = [];
  var vertices = [];
  var lines = []; // Not actually a wireframe mesh, but rendered nonetheless
  var faces = []; 
  
  // Other three.js related variables
  var mouse; // Vector2 representing mouse position in canvas space
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

    //  drag = new DragControls(vertices, camera, renderer.domElement);
    transform = new TransformControls(camera, renderer.domElement);
    
    raycaster = new THREE.Raycaster();
    
    mouse = new THREE.Vector3(0, 0, 0.5);
    
    hoverMesh = new THREE.Mesh(
      new THREE.SphereBufferGeometry(0.1, 20, 20),
      new THREE.MeshBasicMaterial({ color: 0x0000ff })
    );
    planeMesh = new THREE.Mesh(
      new THREE.PlaneBufferGeometry(20, 20),
      new THREE.MeshBasicMaterial({
        color: 0x333333,
        side: THREE.DoubleSide,
        transparent: true,
        opacity: 0.3
    }));
    
    planes = initPlanes();
    
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
    var faceTool = document.getElementById("face");
    
    var min = new THREE.Vector3(-10, -10, -10);
    var max = new THREE.Vector3(10, 10, 10);

    moveTool.addEventListener("click", startMove, false);
    pointTool.addEventListener("click", startPoint, false);
    lineTool.addEventListener("click", startLine, false);
    faceTool.addEventListener("click", startFace, false);
    
    transform.addEventListener("dragging-changed", (event) => {
      orbit.enabled = !event.value;
    }, false);
    transform.addEventListener("objectChange", (event) => {
      transform.object.position.clamp(min, max);
    }, false);

    var lastButton = moveTool;

    moveTool.click();

    // CHANGE TOOLS
    function click(button) {
      lastButton.disabled = false;
      button.disabled = true;
      lastButton = button;
      removeListeners();
    }

    function removeListeners() {
      stopMove();
      stopPoint();
      stopLine();
    }
   
    function select(mesh) {
      if(selected != null) {
        selected.material.color.setHex(0xff0000);
      }
      selected = mesh;
      selected.material.color.setHex(0xffff00);
      if(transform.enabled) {
        transform.attach(selected);
      }
    } 
    
    function deselect() {
      if(selected != null) {
        selected.material.color.setHex(0xff0000);
        selected = null;
        if(transform.enabled) {
          transform.detach();
        }
      }
    }
    
    // DRAG & TRANSFORM CONTROLS FUNCTIONS
    var selected, snapping = true;
    /*
    drag.addEventListener("dragstart", (event) => {
      orbit.enabled = false;
    }, false);
    
    drag.addEventListener("dragend", (event) => {
      orbit.enabled = true;
    }), false;
    
    function axisX(active) {
      transform.showX = active;
      //axis();
    }
    
    function axisY(active) {
      transform.showY = active;
      //axis();
    }
    
    function axisZ(active) {
      transform.showZ = active;
      //axis();
    }

    function swapAxis() {
      transform.showX = !transform.showX;
      transform.showY = !transform.showY;
      transform.showZ = !transform.showZ;
      //axis();
    }
       
    function axis() {
      if(transform.showX && transform.showY && transform.showZ) {
        drag.enabled = true;
        drag.activate();
      } else {
        drag.enabled = false;
        drag.deactivate();
      }
    }
    */
    function snap(position, precision) {
      var x = Math.round(position.x);
      var y = Math.round(position.y);
      var z = Math.round(position.z);
      
      
      if(!(position.x > x - precision && x + precision > position.x)) {
        x = position.x;
      }
      
      if(!(position.y > y - precision && y + precision > position.y)) {
        y = position.y;
      }
      
      if(!(position.z > z - precision && z + precision > position.z)) {
        z = position.z;
      }
      
      return new THREE.Vector3(x, y, z);
    }
    
    function transformSnap(enabled) {
      if(enabled) {
        transform.setTranslationSnap(0.25);
        transform.setRotationSnap(THREE.MathUtils.degToRad(15));
        transform.setScaleSnap(0.25);
      } else {
        transform.setTranslationSnap(null);
        transform.setRotationSnap(null);
        transform.setScaleSnap(null);
      }
    }
    
    // MOVE TOOL
    function startMove() {
      click(moveTool);
      scene.add(transform);
    }
    
    function stopMove() {
      transform.detach();
      scene.remove(transform);      
    }

    // POINT TOOL
    function startPoint() {
      click(pointTool);
      transform.enabled = true;
      scene.add(transform);
      container.addEventListener("mousemove", pointMove, false);
      container.addEventListener("mousedown", pointDown, false);
      container.addEventListener("keydown", pointKey, false);
    }

    var pointMove = function () {
      var intersects = raycaster.intersectObjects(vertices);
      
      if(intersects.length > 0 || transform.dragging) {
        scene.remove(hoverMesh);
        return;
      }
      
      var intersects = raycaster.intersectObjects(planes);
      
      if (intersects.length > 0) {
        scene.add(hoverMesh);
        
        hoverMesh.position.copy(snap(intersects[0].point, 0.1));
      } else {
        scene.remove(hoverMesh);
      }
    }

    var pointDown = function (event) {
      var intersects = raycaster.intersectObjects(vertices);
      
      if(intersects.length > 0 && !transform.dragging) {
        if(intersects[0].object !== selected) {
          select(intersects[0].object);
        }
        return;
      }
      
      intersects = raycaster.intersectObjects(planes);
      
      if(intersects.length > 0 && !transform.dragging) {
        let point = hoverMesh.clone();
        point.material = hoverMesh.material.clone();
        scene.add(point);
        vertices.push(point);
        transform.attach(point);
        
        select(point);
        
      } else if(!transform.dragging) {
        deselect();
      }
    }
    
    var pointKey = function (event) {
      if((event.key === "Backspace" || event.key === "Delete" || event.key ==="Del") && selected != null) {
        scene.remove(selected);
        let i = vertices.indexOf(selected);
        if(i > -1) {
          vertices.splice(i, 1);
        }
        deselect();
      }
    }

    function stopPoint() {
      deselect();
      transform.enabled = false;
      scene.remove(transform);
      
      scene.remove(hoverMesh);
      container.removeEventListener("mousemove", pointMove, false);
      container.removeEventListener("mousedown", pointDown, false);
      container.removeEventListener("keydown", pointKey, false);
    }

    // LINE TOOL
    var points = [];
    
    function startLine() {
      click(lineTool);

      container.addEventListener("mousedown", lineDown, false);
    }

    var lineMove = function () {
      //a    
    }

    var lineDown = function () {
      var intersects = raycaster.intersectObjects(vertices);
      
      if(intersects.length > 0) {
        select(intersects[0].object);
        
        points.push(selected.position);
        
        if(points.length > 1) {
          let geometry = new THREE.Geometry()
          geometry.vertices = points;
          let line = new THREE.Line( geometry, new THREE.LineBasicMaterial({color: 0xffffff}) )
          scene.add(line);
          lines.push(line);
          points = [];
        }
      }
    }

    function stopLine() {
      points = [];
      container.removeEventListener("mousedown", lineDown, false);
    }
    
    // FACE TOOL
    function startFace() {
      click(faceTool);
      
      container.addEventListener("mousedown", faceDown, false);
    }
    
    var faceDown = function (){
      var intersects = raycaster.intersectObjects(vertices);
      
      if(intersects.length > 0) {
        select(intersects[0].object);
        
        points.push(selected.position);
        console.log(selected.position);
        
        if(selected.position === points[0] && points.length >= 3) {
          selected.position.x += 0.01;
          selected.position.y += 0.01;
          selected.position.z += 0.01;
          let geometry = new THREE.Geometry();
          
          points.pop();
          geometry.vertices = points;          
          
          let triangles = THREE.ShapeUtils.triangulateShape(points, []);
          
          console.log(triangles.length)
          
          for( var i = 0; i < triangles.length; i++ ){
            geometry.faces.push(new THREE.Face3(triangles[i][0], triangles[i][1], triangles[i][2]));
          }
          
          let face = new THREE.Mesh(geometry,
            new THREE.MeshBasicMaterial({
              color: 0x2989D6,
              side: THREE.DoubleSide
          }));
          scene.add(face);
          faces.push(face);
          points = [];
          selected.position.y -= 0.01;
        }
      }
    }
    
    function stopFace() {
      points = [];
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
  
  function initPlanes() {
    var grid = [];
    var planes = [];

    planes.push(planeMesh.clone());
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
    for(line of lines) {
      line.geometry.verticesNeedUpdate = true;
    }
    for(face of faces) {
      face.geometry.elementsNeedUpdate = true;
    }
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
