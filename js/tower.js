/* global PIXI */
var START_X = 300;
function Tower(x, y, user, maxTowers) {
    PIXI.Container.call(this);

    this.position.x = START_X + x;
    var mid = new PIXI.Sprite(PIXI.utils.TextureCache["img/mid_tower.png"]);
    var bottom = new PIXI.Sprite(PIXI.utils.TextureCache["img/bottom_tower.png"]);
    var top = new PIXI.Sprite(PIXI.utils.TextureCache["img/top_tower.png"]);
    this.position.y = (maxTowers - user.towers) * mid.height;/*space for the little buildings*/
    this.addChild(top);
    var tempHeight = top.height;
    for (i = 0; i < user.towers; i++) {
        var midTemp = new PIXI.Sprite(PIXI.utils.TextureCache["img/mid_tower.png"]);
        midTemp.position.y = tempHeight;
        tempHeight += mid.height;
        this.addChild(midTemp);
    }
    bottom.position.y = tempHeight;
    this.addChild(bottom);
    //
    //
    // this.addChild(bottom);

  //  top.position.y = -1 * (top.height + tempHeight - mid.height);
  //  this.addChild(top);
}

Tower.prototype = Object.create(PIXI.Container.prototype);