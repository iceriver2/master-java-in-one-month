/**
 * 解析Java API文档页面的表格，生成属性和方法的列表，免去ctrl+c/ctrl+v的工作。
 * 出于便捷考虑，不使用“读取网页输出文件”的方式，读取一个文本处理后输出到屏幕。
 * 
 * parse_class_methods 解析类的方法表格，只接口 <table> 内容（不能处理构造函数table这种特殊情况）
 * parse_classes 解析 comapact profile 中某包的类列表，只接受 <tbody> 的内容
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

let result;
result = parse_class_methods();
if (result) process.exit();
result = parse_classes();
process.exit();

function parse_class_methods() {
  try {
    console.log('trying to parse methods in class ...');

    const content = fs.readFileSync(infile, "utf8");
    if (!content) {
      console.log('no content found ! exiting...');
      // process.exit();
      return false;
    }
  
    const trs = content.replace(/[\n\r]/g, '').match(/<tr id=.*?<\/tr>/g);
    if (!trs || !trs.length) {
      console.log('no valid content found ! exiting...');
      // process.exit();
      return false;
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

    return true;
  } catch (e) {
    console.log('some exception:' + e.message);
    // process.exit();
    return false;
  }
}

function parse_classes() {
  try {
    console.log('trying to parse classes ...');

    const content = fs.readFileSync(infile, "utf8");
    if (!content) {
      console.log('no content found ! exiting...');
      // process.exit();
      return false;
    }

    const trs = content.replace(/[\n\r]/g, '').match(/<tr.*?<\/tr>/g);
    if (!trs || !trs.length) {
      console.log('no valid content found ! exiting...');
      // process.exit();
      return false;
    }
  
    let output = '';
    trs.forEach((tr, i) => {
      const code = tr.match(/<td class="colFirst">.*?<\/td>/g); // 只取前2
      let cls = '';
      cls += code[0].replace(/(<.*?>)/g, '') + ' ';
      cls = cls.replace(/&nbsp;/g, ' ');
      cls = cls.replace(/&lt;/g, '<');
      cls = cls.replace(/&gt;/g, '>');
      cls = cls.replace(/ +/g, ' ');
      cls = cls.replace(/ +$/, '');
      output += `${cls}\n`;
    });
    fs.writeFileSync(outfile, output, "utf8");

    return true;
  } catch (e) {
    console.log('some exception:' + e.message);
    // process.exit();
    return false;
  }
}



