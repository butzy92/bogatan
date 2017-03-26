var scaleStep = 0.1;
var maxScale = 10;
var minScale = 0;
var initScale = 0;
var precisionStep = 0.04;

PIXI.loader
        .add("img/atmosphere.png")
        .add("img/towers.png")
        .add("img/top_tower.png")
        .add("img/mid_tower.png")
        .add("img/1px.png")
        .add("img/bottom_tower.png")
        .add("towers", "towers.json")
        .load(function () {

        });


function init() {

    renderer = PIXI.autoDetectRenderer(981, 724, {backgroundColor: 0x0044aa});
    document.body.appendChild(renderer.view);
    stage = new PIXI.Container();
    texture = new PIXI.Container();


    var background = new Background();
    texture.interactive = true;
    texture.buttonMode = true;
    texture.on("mousemove", mouseMove)
            .on("mousedown", mouseDown)
            .on("mouseup", mouseUp)
            .on("mouseupoutside", mouseUp)
            .on("pointermove", mouseMove)
            .on("pointerdown", mouseDown)
            .on("pointerup", mouseUp);

    document.addEventListener("mousewheel", wheelDown, false);
    texture.addChild(background);

//    for (var i = 0; i < 10; i++) {
//        texture.addChild(new Tower(i * 500, -180));
//    }
    var towers = PIXI.loader.resources.towers.data.sort(function (a, b) {
        return b.towers - a.towers;
    });

    towers.forEach(function (item, index) {
        texture.addChild(new Tower(index * 500, 555, item));
    });

   


    if (towers.length !== 0) {
        stage.position.y = towers[0].towers * 78
    }
    var texture1px = PIXI.utils.TextureCache["img/1px.png"]
   
    var emptyContainer = new PIXI.Sprite(texture1px);
    emptyContainer.position.y = -100000000000000000000000
    console.log(emptyContainer.height)
    texture.addChild(emptyContainer);


    stage.addChild(texture);
    texture.hitArea = stage.getLocalBounds();
    requestAnimationFrame(update);
    renderer.render(stage);
}

function update() {
    renderer.render(stage);
    requestAnimationFrame(update);
}
function mouseMove(e) {
    if (this.dragging) {
        var newPosition = this.data.getLocalPosition(this.parent);
        var newX = newPosition.x - this.dragging.x;
        var newY = newPosition.y - this.dragging.y;
        if (texture.position.x + newX < 0) {
            texture.x += newX;
        } else {
            texture.position.x = 0;
        }
//        if (texture.position.y + newY > 0) {
//
//            texture.y += newY;
//
//        } else {
//            texture.position.y = 0;
//        }
        texture.y += newY;
        this.dragging = newPosition;
    }
}

function mouseUp() {
    this.data = null;
    this.dragging = false;
}
function mouseDown(event) {
    this.data = event.data;
    this.dragging = this.data.getLocalPosition(this.parent);
}

function wheelDown(e) {
    var e = window.event || e;
    var up = e.deltaY > 0;
//    if ((up && initScale === maxScale) || (!up && initScale === minScale)) {
//        return;
//    }
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