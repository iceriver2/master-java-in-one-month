/**
 * 解析Java API文档页面的表格，生成属性和方法的列表，免去ctrl+c/ctrl+v的工作。
 * 出于便捷考虑，不使用“读取网页输出文件”的方式，读取一个文本处理后输出到屏幕。
 * 文本内容为API文档中的table，而且不处理构造函数table这种特殊情况
 * 
 * 命令：node parse_doc_table.js some.txt
 */

const fs = require("fs");

const infile = process.argv[2]; // 0-node, 1-parse_doc_table.js
const outfile = process.argv[3]; // 0-node, 1-parse_doc_table.js
if (!infile || !outfile) {
  console.log('no in or out file found !');
  process.exit();
}

try {
  const content = fs.readFileSync(infile, "utf8");
  if (!content) {
    console.log('no content found !');
    process.exit();
  }

  const trs = content.replace(/[\n\r]/g, '').match(/<tr id=.*?<\/tr>/g);
  if (!trs || !trs.length) {
    console.log('no valid content found !');
    process.exit();
  }

  let output = '';
  trs.forEach((tr, i) => {
    const code = tr.match(/<code>.*?<\/code>/g); // 只取前2
    let method = '';
    method += code[0].replace(/(<.*?>)/g, '') + ' ';
    method += code[1].replace(/(<.*?>)/g, '') + ' ';
    method = method.replace(/&nbsp;/g, ' ');
    method = method.replace(/&lt;/g, '<');
    method = method.replace(/&gt;/g, '>');
    method = method.replace(/ +/g, ' ');
    method = method.replace(/ +$/, '');
    output += `\`${method}\`\n`;
  });
  fs.writeFileSync(outfile, output, "utf8");
} catch (e) {
  console.log('some exception:' + e.message);
  process.exit();
}

