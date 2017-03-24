var scaleStep = 0.1;
var maxScale = 4;
var minScale = 0;
var initScale = 0;

function init() {

    renderer = PIXI.autoDetectRenderer(981, 724, {backgroundColor: 0x34495e});
    document.body.appendChild(renderer.view);

    stage = new PIXI.Container();

    var farTexture = PIXI.Texture.fromImage("img/atmosphere.png", PIXI.SHAPES.RECT, 1);
    far = new PIXI.Sprite(farTexture);

    far.interactive = true;
    far.buttonMode = true;

    far.on("mousemove",mouseMove())
        .on("mousedown", mouseDown())
        .on("mouseup", mouseUp())
        .on("pointermove",mouseMove())
        .on("pointerdown", mouseDown())
        .on("pointerup", mouseUp());

    document.addEventListener("mousewheel", wheelDown, false);
    stage.addChild(far);
    // var midTexture = PIXI.Texture.fromImage("img/towers.png");
    // mid = new  PIXI.extras.TilingSprite(
    //     midTexture,
    //     981,
    //     500
    // );
    // mid.position.x = 0;
    // mid.position.y = 128;
    // mid.tilePosition.x = 0;
    // mid.tilePosition.y = 0;
    // stage.addChild(mid);
    requestAnimationFrame(update);

    renderer.render(stage);

    function mouseMove(){
        console.log("misc mouse")
    }
    function mouseDown(event){
        //this.data = event.data
        this.dragging = true;
        console.log("misc jos")
    }

    function mouseUp(){
        //this.data = null
        this.dragging = false
        console.log("misc sus")
    }


}

function update() {
    //  far.tilePosition.x -= 0.128;
//    mid.tilePosition.x -= 0.64;

    renderer.render(stage);

    requestAnimationFrame(update);
}

function wheelDown(e) {
    var e = window.event || e
    var up = e.deltaY > 0;
    console.log("am intrat")
    console.log(e)

    if ((up && initScale == maxScale) || (!up && initScale == minScale)){
        return ;
    }



    if (up) {
        stage.scale.x += scaleStep;
        stage.scale.y += scaleStep;
        initScale++;
    } else {
        stage.scale.x -= scaleStep;
        stage.scale.y -= scaleStep;
        initScale--;
    }

}




function addDragNDrop() {
    stage.interactive = true;

    var isDragging = false,
        prevX, prevY;

    stage.mousedown = function (moveData) {
        var pos = moveData.data.global;
        //  prevX = pos.x;
        //   prevY = pos.y;
        //    isDragging = true;
    };

    stage.mousemove = function (moveData) {
        // if (!isDragging) {
        //     return;
        // }
        // var pos = moveData.data.global;
        // var dx = pos.x - prevX;
        // var dy = pos.y - prevY;

        console.log("dx=" + dx);
        console.log("stage.position.x=" + stage.position.x);
        console.log("far.position.x=" + far.position.x);
        console.log("pos.x=" + pos.x);
        console.log("prevX=" + prevX);
        console.log("dy=" + dy);
        // if (far.position.x != 0) {
        //     far.position.x += dx;
        // }
        //
        // if (far.position.y != 0) {
        //     far.position.y += dy;
        // }

        //  stage.position.x += dx;
        // stage.position.y += dy;
        //     prevX = pos.x;
        //    prevY = pos.y;
    };

    stage.mouseup = function (moveDate) {
        isDragging = false;
    };
}

 