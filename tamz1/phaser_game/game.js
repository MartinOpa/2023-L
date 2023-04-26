var config = {
    type: Phaser.AUTO,
    width: Math.min(window.innerWidth, window.outerWidth),
    height: Math.min(window.innerHeight, window.outerHeight),
    physics: {
        default: 'arcade',
        arcade: {
            gravity: { y: 0 },
            debug: false
        }
    },    
    scene: {
        preload: preload,
        create: create,
        update: update
    },
    audio: {
        disableWebAudio: true
    },
    debug: true
};

var game = new Phaser.Game(config);

var abovebkgrndLayer;
var abovebkgrndLayer2;
var backgroundLayer;
var collisionLayer;
var itemsLayer;

var map;
var coinsCollected = 0;
var bestCollected;
var text;
var player;
var items;
var bombs;
var gameOver = false;
var move_ctl = false;
var left,right,up,down;
var sharedVolume;
var music;
var coinSound;
var bombSound;
var muteSoundsTxt;
var muteSounds = false;
var muteText = 'mute';

var isCollision;
var itemID = 9;

function preload ()
{
    this.load.spritesheet('robot', 'assets/lego.png',
        { frameWidth: 37, frameHeight: 48 } ); 

    this.load.spritesheet('items', 'assets/items.png', { frameWidth: 32, frameHeight: 32 } ); 
    this.load.image('bomb', 'assets/bomb.png');
    this.load.image('tiles', 'assets/map_tiles.png');
    this.load.tilemapTiledJSON('json_map', 'assets/json_map.json');
    this.load.audio('cat', 'assets/cat.mp3');
    this.load.audio('coin', 'assets/coin.mp3');
    this.load.audio('bomb', 'assets/bomb.mp3');
    bestCollected = localStorage.getItem('highScore');
}

function resize (width, height)
{/*
    if (width === undefined) { width = game.config.width; }
    if (height === undefined) { height = game.config.height; }
    //console.log('W: ' +  width + ', H: ' + height); 
    if (width < backgroundLayer.width || height < backgroundLayer.height) {
		map.scene.cameras.main.zoom = 0.5;
		map.scene.cameras.main.setPosition(-width/2, -height/2);
  } else {
		map.scene.cameras.main.zoom = 1;
		map.scene.cameras.main.setPosition(0,0);
	}
    //backgroundLayer.setSize(width, height);
    map.scene.cameras.resize(width/map.scene.cameras.main.zoom, height/map.scene.cameras.main.zoom);
	if (game.renderer.type === Phaser.WEBGL){	
		game.renderer.resize(width, height);
	}
    updateText();
*/
}		
function create ()
{
    isCollision = 0;
    map = this.make.tilemap({ key: 'json_map' });
    var tiles = map.addTilesetImage('map_tiles','tiles');

    backgroundLayer = map.createDynamicLayer('background', tiles, 0, 0);
    abovebkgrndLayer = map.createDynamicLayer('abovebkgrnd', tiles, 0, 0);
    abovebkgrndLayer2 = map.createDynamicLayer('abovebkgrnd2', tiles, 0, 0);
    collisionLayer = map.createDynamicLayer('collision', tiles, 0, 0);
    collisionLayer.setCollisionByExclusion([ -1 ]);
    map.scene.cameras.main.backgroundColor = Phaser.Display.Color.HexStringToColor('#C2B280');
    
    items = this.physics.add.sprite(100, 150, 'items', 9);
    items.setBounce(0.1);

    bombs = this.physics.add.image(100, 150, 'bomb');
    bombs.setBounce(0.3);
    
    player = this.physics.add.sprite(100, 450, 'robot');
    player.setBounce(0.1);
    
    
    this.physics.add.collider(player, collisionLayer);
    this.physics.add.overlap(player, backgroundLayer);

    this.physics.add.collider(player, bombs, bombsHandler);
    this.physics.add.collider(bombs, collisionLayer, bombBouncer);

    backgroundLayer.setCollisionBetween(1, 15);    
       
    this.physics.add.overlap(player, items, collisionHandler);
    
    this.cameras.main.startFollow(player);    
 
    text = this.add.text(game.canvas.width/2, 32, '', {
        fontSize: '3em',
        fontFamily: 'fantasy',
        align: 'center',
        boundsAlignH: "center", 
        boundsAlignV: "middle", 
        fill: '#ffffff'
    });
    text.setOrigin(0.5);
    text.setScrollFactor(0);    
    updateText();
    
    this.anims.create({
        key: 'run',
        frames: this.anims.generateFrameNumbers('robot', { start: 0, end: 16 }),
        frameRate: 20,
        repeat: -1
    }); 
    
    cursors = this.input.keyboard.createCursorKeys();  

	this.input.on('pointerdown', function (pointer) { 
		move_ctl = true; 
		pointer_move(pointer); 
	});
	this.input.on('pointerup', function (pointer) { move_ctl = false; reset_move()});
	this.input.on('pointermove', pointer_move);
	window.addEventListener('resize', function (event) {
		resize(Math.min(window.innerWidth, window.outerWidth), Math.min(window.innerHeight, window.outerHeight));
	}, false);		
	resize(Math.min(window.innerWidth, window.outerWidth), Math.min(window.innerHeight, window.outerHeight));

    sharedVolume = 1;

    music = this.sound.add('cat', {volume: sharedVolume/2, loop: true});
    if (!this.sound.locked) {
		music.play()
	}
	else {
		this.sound.once(Phaser.Sound.Events.UNLOCKED, () => {
			music.play()
		})
	}

    coinSound = this.sound.add('coin', {volume: sharedVolume, loop: false});
    bombSound = this.sound.add('bomb', {volume: sharedVolume, loop: false});

    if (bestCollected == undefined) {
        bestCollected = 0;
    }

    bombs.disableBody(true, true);
    bombs.enableBody(true, map.heightInPixels/2, map.widthInPixels/2, true, true);
    bombs.body.setVelocityX(Phaser.Math.Between(-300, 300));
    bombs.body.setVelocityY(Phaser.Math.Between(-300, 300));

    muteSoundsTxt = this.add.text(game.canvas.width/2, 64, '', {
        fontSize: '3em',
        fontFamily: 'fantasy',
        align: 'center',
        boundsAlignH: "center", 
        boundsAlignV: "middle", 
        fill: '#ffffff'
    });
    muteSoundsTxt.setOrigin(0.5);
    muteSoundsTxt.setScrollFactor(0); 
    updateSoundBtn();

    muteSoundsTxt.setInteractive({ useHandCursor: true });
    muteSoundsTxt.on('pointerdown', () => toggleAudio());
}

function pointer_move(pointer) {
		var dx=dy=0;
		var min_pointer=20;
		var min_pointer = (player.body.width + player.body.height) / 4;
		if (move_ctl) {
			reset_move();

			dx = (pointer.x / map.scene.cameras.main.zoom - player.x);
			dy = (pointer.y / map.scene.cameras.main.zoom - player.y);
			
			if (Math.abs(dx) > min_pointer) {
				left = (dx < 0); 
				right = !left; 
			} else { 
				left = right = false;
			}
			if (Math.abs(dy) > min_pointer) {
				up = (dy < 0); 
				down = !up; 
			} else { 
				up = down = false;
			}
		}
}

function reset_move() {
  up = down = left = right = false;
}

function update() {     
	if (move_ctl) { pointer_move(game.input.activePointer); }
	
    if (cursors.left.isDown || left)
    {
        player.body.setVelocityX(-150);
        player.angle = 90;
        player.anims.play('run', true); 
    }
    else if (cursors.right.isDown || right)
    {
        player.body.setVelocityX(150);
        player.angle = 270;
        player.anims.play('run', true); 
    }
    else
    {
        player.body.setVelocityX(0);
    }

    if (cursors.up.isDown || up)
    {
        player.body.setVelocityY(-150);
        player.angle = 180;
        player.anims.play('run', true); 
    }
    else if (cursors.down.isDown || down)
    {
        player.body.setVelocityY(150);
        player.anims.play('run', true); 
        player.angle = 0;
    }
    else
    {
        player.body.setVelocityY(0);
    }

    if (player.body.velocity.x < 0.5 && player.body.velocity.x > -0.5 &&
        player.body.velocity.y < 0.5 && player.body.velocity.y > -0.5)
    {
        player.anims.stop();
    }

}


function updateText() {
	text.setPosition(game.canvas.width/2 / map.scene.cameras.main.zoom, text.height);
    text.setText(
        'Coins collected: ' + coinsCollected + '    Best result: ' + bestCollected
    );
    text.setColor('white');
}

function updateSoundBtn() {
    muteSoundsTxt.setPosition(game.canvas.width/2 / map.scene.cameras.main.zoom, muteSoundsTxt.height + game.canvas.height-60 / map.scene.cameras.main.zoom);
    muteSoundsTxt.setText(muteText);
    muteSoundsTxt.setColor('white');
}

function collisionHandler(player, item) {   
	if (!muteSounds) {
        coinSound.play();
    }

    coinsCollected += 1;
    if (coinsCollected > bestCollected) { 
        bestCollected = coinsCollected;
        localStorage.setItem('highScore', bestCollected);
    }
    updateText(); 
    item.disableBody(true, true);
      
    if (item.body.enable == false) {
        var h = map.heightInPixels-40;
        var w = map.widthInPixels-40;
        var itemX = Phaser.Math.Between(40, w); 
        var itemY = Phaser.Math.Between(60, h);
        while (
            (itemX > w-(w/2) && itemY > h-(h/4)) ||
            (itemX > w/4 + 40 && itemX < w/4 - 40 && itemY > h-(h/4)+40) ||
            (itemX < w/4 + 130 && itemX > w/4 - 130 && itemY > h/5-60 && itemY < h/5+20) ||
            (itemX > w/2 + 100 && itemX < w/2 + 230 && itemY < h/5 - 20) ||
            (itemX > w-40 && itemY > h/3)
        ) {
            var itemX = Phaser.Math.Between(60, w-20); 
            var itemY = Phaser.Math.Between(60, h-20);
        }
        var itemID = Phaser.Math.Between(7, 11);
        item.setFrame(itemID);
        item.enableBody(true, itemX, itemY, true, true);
    }
}

function bombsHandler(player, bombs) {      
    if (!muteSounds) {
        bombSound.play();
    }
    coinsCollected = 0;
    updateText();
}

function bombBouncer(bombs, collisionLayer) {
    bombs.setVelocityX(Phaser.Math.Between(-300, 300));
    bombs.setVelocityY(Phaser.Math.Between(-300, 300));
}

function toggleAudio() {
    muteSounds = !muteSounds;
    if (muteSounds) {
        muteText = 'unmute';
        music.stop();
    }
    else {
        muteText = 'mute';
        music.play();
    }
    updateSoundBtn();
}
