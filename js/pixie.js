function init() {
    stage = new PIXI.Container();
    renderer = PIXI.autoDetectRenderer(
        981,
        724,
        {view: document.getElementById("game-canvas")}
    );
    var width = document.getElementById("game-canvas").width;
    var farTexture = PIXI.Texture.fromImage("img/atmosphere.png", PIXI.SHAPES.RECT, 1);
    far = new PIXI.extras.TilingSprite(
        farTexture,
        981,
        724
    );
    far.position.x = 0;
    far.position.y = 0;
    far.tilePosition.x = 0;
    far.tilePosition.y = 0;

    farTexture.interactive = true;
    farTexture.buttonMode = true;


    farTexture.on('pointerdown', onDragStart)
        .on('pointerup', onDragEnd)
        .on('pointerupoutside', onDragEnd)
        .on('pointermove', onDragMove);


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
}


function onDragStart(event) {
    console.log("a intraaaat")
    // store a reference to the data
    // the reason for this is because of multitouch
    // we want to track the movement of this particular touch
    this.data = event.data;
    this.alpha = 0.5;
    this.dragging = true;
}

function onDragEnd() {
    this.alpha = 1;
    this.dragging = false;
    // set the interaction data to null
    this.data = null;
}

function onDragMove() {
    if (this.dragging) {
        var newPosition = this.data.getLocalPosition(this.parent);
        this.tilePosition.x = newPosition.x;
        this.tilePosition.y = newPosition.y;
    }
}


function update() {
  //  far.tilePosition.x -= 0.128;
//    mid.tilePosition.x -= 0.64;

    renderer.render(stage);

   requestAnimationFrame(update);
}