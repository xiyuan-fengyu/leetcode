import {FindIndex_KR} from "./FindIndex_KR";
import {FindIndex_BF} from "./FindIndex_BF";
import {FindIndex_KMP} from "./FindIndex_KMP";
import {FindIndex_Sunday} from "./FindIndex_Sunday";
import {FindIndex_Horspool} from "./FindIndex_Horspool";

function randomStr(len: number) {
    const chars = "0123456789abcdefghijklmnopqrstuvwxyz0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    let str = "";
    for (let i = 0; i < len; i++) {
        str += chars[Math.floor(Math.random() * chars.length)];
    }
    return str;
}

const testNum = 100000;
const testCases: {str: string, pattern: string, index: number}[] = [];
for (let i = 0; i < testNum; i++) {
    const testCase = {
        str: randomStr(Math.floor(Math.random() * 100)),
        pattern: null,
        index: -1
    };
    if (Math.random() < 0.25) {
        testCase.pattern = randomStr(Math.floor(Math.random() * 50));
    }
    else {
        const index = Math.floor(Math.random() * testCase.str.length);
        const end = Math.floor(Math.random() * (testCase.str.length - index + 1) + index);
        testCase.pattern = testCase.str.substring(index, end);
    }
    testCase.index = testCase.str.indexOf(testCase.pattern);
    testCases.push(testCase);
}

const solutions = [
    FindIndex_BF,
    FindIndex_KMP,
    FindIndex_KR,
    FindIndex_Horspool,
    FindIndex_Sunday,
];

{
    let startTime = new Date().getTime();
    for (let testCase of testCases) {
        testCase.str.indexOf(testCase.pattern);
    }
    console.log(`String.indexOf cost: ${new Date().getTime() - startTime}ms`);
}
for (let solution of solutions) {
    let startTime = new Date().getTime();
    for (let testCase of testCases) {
        (solution as any).find(testCase.str, testCase.pattern);
        // const index = solution.find(testCase.str, testCase.pattern);
        // if (index != testCase.index) {
        //     console.log(`${solution.name}.find(${JSON.stringify(testCase.str)}, ${JSON.stringify(testCase.pattern)}) = ${index} != ${testCase.index}`);
        //     process.exit(-1);
        // }
    }
    console.log(`${solution.name} cost: ${new Date().getTime() - startTime}ms`);
}
