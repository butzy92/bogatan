var scaleStep = 0.01;
var maxScale = 10;
var minScale = 0;
var initScale = 0;
var precisionStep = 0.04;
var HEIGHT_OF_TOWER = 78;
var CANVAS_HEIGHT = 724;
var CANVAS_WIDTH = 981;
PIXI.loader
    .add("img/atmosphere.png")
    .add("img/towers.png")
    .add("img/top_tower.png")
    .add("img/mid_tower.png")
    .add("img/1px.png")
    .add("img/bottom_tower.png")
    .add("towers", "towers.json")
    .load(function () {
        init()
    });


function init() {

    renderer = PIXI.autoDetectRenderer(CANVAS_WIDTH, CANVAS_HEIGHT, {backgroundColor: 0x0044aa});
    document.body.appendChild(renderer.view);
    stage = new PIXI.Container();
    texture = new PIXI.Container();
    texture.interactive = true;
    texture.buttonMode = true;


    document.addEventListener("mousewheel", wheelDown, false);
    //  texture.addChild(new Background());

    var towers = PIXI.loader.resources.towers.data.sort(function (a, b) {
        return b.towers - a.towers;
    });

    texture.on("mousemove", mouseMove)
        .on("mousedown", mouseDown)
        .on("mouseup", mouseUp)
        .on("mouseupoutside", mouseUp)
        .on("pointermove", mouseMove)
        .on("pointerdown", mouseDown)
        .on("pointerup", mouseUp);

    towers.forEach(function (item, index) {
        texture.addChild(new Tower(index * 500, 555, item, towers[0].towers));
    });

    // if (towers.length !== 0) {
    //     stage.position.y = towers[0].towers * HEIGHT_OF_TOWER
    // }
    // var emptyContainer = new PIXI.Sprite();
    // emptyContainer.position.y = -100000000000000000000000
    // texture.addChild(emptyContainer);


    stage.addChild(texture);
    texture.hitArea = stage.getLocalBounds();
    requestAnimationFrame(update);
    renderer.render(stage);
}

function update() {
    renderer.render(stage);
    requestAnimationFrame(update);
}
function mouseMove() {
    if (this.dragging) {
        var newPosition = this.data.getLocalPosition(this.parent);
        var newX = newPosition.x - this.dragging.x;
        var newY = newPosition.y - this.dragging.y;
        if (texture.position.x + newX < 0) {
            texture.x += newX;
        } else {
            texture.position.x = 0;
        }

        console.log("newY" + newY);
        console.log("texture.position.y + newY= " + (texture.position.y + newY));
        console.log("biggestTower.height - CANVAS_HEIGHT" + (CANVAS_HEIGHT - texture.height));
        console.log("texture.height " + texture.height);
        console.log("Math.min((CANVAS_HEIGHT - texture.height), (CANVAS_HEIGHT * -1) " + Math.min((CANVAS_HEIGHT - texture.height), (CANVAS_HEIGHT * -1)));
        console.log("Math.min((CANVAS_HEIGHT - texture.height), (CANVAS_HEIGHT * -1) " + Math.min((CANVAS_HEIGHT - texture.height), (CANVAS_HEIGHT * -1)));

        texture.position.y += newY;

        // if (CANVAS_HEIGHT < texture.height) {
            if (texture.position.y + newY < 0) {
                if (texture.position.y + newY > (CANVAS_HEIGHT - texture.height)) {
                    texture.position.y += newY;
                } else {
                    texture.position.y = (CANVAS_HEIGHT - texture.height);
                }

            } else {
                if (CANVAS_HEIGHT > texture.height) {
                   if(texture.position.y + newY < (CANVAS_HEIGHT - texture.height)){
                       texture.position.y = CANVAS_HEIGHT - texture.height;
                   }else{
                       texture.position.y += newY;
                   }

                }else{
                    texture.position.y = 0;
                }

            }
        // }else{
        //
        // }

        //texture.y += newY;

        // else {
        //     texture.position.y = HEIGHT_OF_TOWER * -1;
        // }
        console.log(texture.position.y)
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
    console.log(this.dragging)
}

function wheelDown(e) {
    var e = window.event || e;
    var up = e.deltaY > 0;
//    if ((up && initScale === maxScale) || (!up && initScale === minScale)) {
//        return;
//    }
    if (up) {
        texture.scale.x += scaleStep;
        texture.scale.y += scaleStep;
        initScale++;
    } else {
        texture.scale.x -= scaleStep;
        texture.scale.y -= scaleStep;
        initScale--;
    }
}