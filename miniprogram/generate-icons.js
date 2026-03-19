const fs = require('fs');
const path = require('path');

// 简单的 PNG 文件生成 (1x1 像素纯色块，用于占位)
function createSimplePNG(color, size, filename) {
    // PNG 文件头 + IHDR 块 + IDAT 块 + IEND 块
    // 这里创建一个简化的单色 PNG
    
    const width = size;
    const height = size;
    
    // PNG 签名
    const signature = Buffer.from([137, 80, 78, 71, 13, 10, 26, 10]);
    
    // IHDR 块
    const ihdrData = Buffer.alloc(13);
    ihdrData.writeUInt32BE(width, 0);
    ihdrData.writeUInt32BE(height, 4);
    ihdrData.writeUInt8(8, 8); // bit depth
    ihdrData.writeUInt8(2, 9); // color type (RGB)
    ihdrData.writeUInt8(0, 10); // compression
    ihdrData.writeUInt8(0, 11); // filter
    ihdrData.writeUInt8(0, 12); // interlace
    
    const ihdrCrc = crc32(Buffer.concat([Buffer.from('IHDR'), ihdrData]));
    const ihdrLength = Buffer.alloc(4);
    ihdrLength.writeUInt32BE(13);
    const ihdrChunk = Buffer.concat([ihdrLength, Buffer.from('IHDR'), ihdrData, ihdrCrc]);
    
    // IDAT 块 (压缩的图像数据)
    const rawData = [];
    for (let y = 0; y < height; y++) {
        rawData.push(0); // filter byte
        for (let x = 0; x < width; x++) {
            rawData.push(color.r);
            rawData.push(color.g);
            rawData.push(color.b);
        }
    }
    const compressed = require('zlib').deflateSync(Buffer.from(rawData));
    
    const idatCrc = crc32(Buffer.concat([Buffer.from('IDAT'), compressed]));
    const idatLength = Buffer.alloc(4);
    idatLength.writeUInt32BE(compressed.length);
    const idatChunk = Buffer.concat([idatLength, Buffer.from('IDAT'), compressed, idatCrc]);
    
    // IEND 块
    const iendCrc = crc32(Buffer.from('IEND'));
    const iendLength = Buffer.alloc(4);
    iendLength.writeUInt32BE(0);
    const iendChunk = Buffer.concat([iendLength, Buffer.from('IEND'), iendCrc]);
    
    // 组合所有部分
    const png = Buffer.concat([signature, ihdrChunk, idatChunk, iendChunk]);
    
    fs.writeFileSync(filename, png);
    console.log(`✓ Created: ${filename} (${size}x${size}, RGB: ${color.r},${color.g},${color.b})`);
}

// CRC32 计算
function crc32(data) {
    let crc = 0xffffffff;
    const table = getCrcTable();
    for (let i = 0; i < data.length; i++) {
        crc = table[(crc ^ data[i]) & 0xff] ^ (crc >>> 8);
    }
    return Buffer.from([(crc ^ 0xffffffff) >>> 24, ((crc ^ 0xffffffff) >>> 16) & 0xff, ((crc ^ 0xffffffff) >>> 8) & 0xff, (crc ^ 0xffffffff) & 0xff]);
}

let crcTable = null;
function getCrcTable() {
    if (crcTable) return crcTable;
    crcTable = new Uint32Array(256);
    for (let i = 0; i < 256; i++) {
        let c = i;
        for (let j = 0; j < 8; j++) {
            c = (c & 1) ? (0xedb88320 ^ (c >>> 1)) : (c >>> 1);
        }
        crcTable[i] = c >>> 0;
    }
    return crcTable;
}

// 创建图标
const imagesDir = path.join(__dirname, 'images');

console.log('正在生成小程序图标...\n');

// logo.png (160x160) - 蓝色背景
createSimplePNG({r: 75, g: 123, b: 255}, 160, path.join(imagesDir, 'logo.png'));

// wechat.png (48x48) - 绿色背景
createSimplePNG({r: 7, g: 193, b: 96}, 48, path.join(imagesDir, 'wechat.png'));

// chat.png (48x48) - 灰色背景 (未选中)
createSimplePNG({r: 204, g: 204, b: 204}, 48, path.join(imagesDir, 'chat.png'));

// chat-active.png (48x48) - 蓝色背景 (选中)
createSimplePNG({r: 75, g: 123, b: 255}, 48, path.join(imagesDir, 'chat-active.png'));

// profile.png (48x48) - 灰色背景 (未选中)
createSimplePNG({r: 204, g: 204, b: 204}, 48, path.join(imagesDir, 'profile.png'));

// profile-active.png (48x48) - 蓝色背景 (选中)
createSimplePNG({r: 75, g: 123, b: 255}, 48, path.join(imagesDir, 'profile-active.png'));

console.log('\n✅ 所有图标已生成完成!');
console.log('请回到微信开发者工具重新编译项目。');
