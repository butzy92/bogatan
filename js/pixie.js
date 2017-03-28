var scaleStep = 0.01;
//var maxScale = 10;
//var minScale = 0;
var initScale = 0;
var CANVAS_HEIGHT = 724;
var CANVAS_WIDTH = 981;
var INITIAL_SPACE = 300;
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
        texture.addChild(new Tower(index, item, towers[0].towers))
    });

    stage.addChild(texture);
    texture.hitArea = stage.getLocalBounds();
    requestAnimationFrame(update);
    renderer.render(stage);
    texture.position.x = INITIAL_SPACE;
}

function update() {
    renderer.render(stage);
    requestAnimationFrame(update);
}
function moveTexture(newMove, texture, horizontal) {
    var CANVAS_SIZE = horizontal ? CANVAS_WIDTH : CANVAS_HEIGHT;
    var TEXTURE_SIZE = horizontal ? texture.width : texture.height;
    var TEXTURE_COORDINATE = horizontal ? texture.position.x : texture.position.y;
    var TEXTURE_MOVED =  (horizontal ? texture.position.x : texture.position.y) + newMove;

    if (CANVAS_SIZE > TEXTURE_SIZE) {
        if (TEXTURE_MOVED < 0) {
            TEXTURE_COORDINATE = 0
        } else {
            if (TEXTURE_MOVED > (CANVAS_SIZE - TEXTURE_SIZE)) {
                TEXTURE_COORDINATE = (CANVAS_SIZE - TEXTURE_SIZE);
            } else {
                TEXTURE_COORDINATE += newMove;
            }
        }
    } else {
        if (TEXTURE_MOVED < 0) {
            if (TEXTURE_MOVED > (CANVAS_SIZE - TEXTURE_SIZE)) {
                TEXTURE_COORDINATE += newMove;
            } else {
                TEXTURE_COORDINATE = (CANVAS_SIZE - TEXTURE_SIZE);
            }

        } else {
            TEXTURE_COORDINATE = 0
        }
    }

    if(horizontal){
        texture.position.x = TEXTURE_COORDINATE
    }else{
        texture.position.y = TEXTURE_COORDINATE
    }
}
function mouseMove() {
    if (this.dragging) {
        var newPosition = this.data.getLocalPosition(this.parent);
        var newX = newPosition.x - this.dragging.x;
        var newY = newPosition.y - this.dragging.y;

        moveTexture(newY, texture, false);
        moveTexture(newX, texture, true);
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
    //noinspection JSDuplicatedDeclaration
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