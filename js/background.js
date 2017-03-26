
function Background(width, height) {
    var farTexture = PIXI.utils.TextureCache["img/atmosphere.png"];
    PIXI.extras.TilingSprite.call(this, farTexture, width, height);
}

function Background() {
    var farTexture = PIXI.utils.TextureCache["img/atmosphere.png"];
    PIXI.extras.TilingSprite.call(this, farTexture, 50000, 724);
}


Background.prototype = Object.create(PIXI.extras.TilingSprite.prototype);