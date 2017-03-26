/* global PIXI */
var START_X = 300;
function Tower(x, y, user) {
    PIXI.Container.call(this);
    this.position.y = y;
    this.position.x = START_X + x;
    var mid = new PIXI.Sprite(PIXI.utils.TextureCache["img/mid_tower.png"]);
    var bottom = new PIXI.Sprite(PIXI.utils.TextureCache["img/bottom_tower.png"]);
    var top = new PIXI.Sprite(PIXI.utils.TextureCache["img/top_tower.png"]);
    this.addChild(bottom);
    var tempHeight = 0;
    for (i = 0; i < user.towers; i++) {
        var midTemp = new PIXI.Sprite(PIXI.utils.TextureCache["img/mid_tower.png"]);
        midTemp.position.y = tempHeight * -1;
        tempHeight += mid.height;
        this.addChild(midTemp);
    }
    top.position.y = -1 * (top.height + tempHeight - mid.height);
    this.addChild(top);
}

Tower.prototype = Object.create(PIXI.Container.prototype);