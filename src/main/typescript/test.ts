import {FindIndex_KR} from "./string/FindIndex_KR";
import {FindIndex_BF} from "./string/FindIndex_BF";
import {FindIndex_KMP} from "./string/FindIndex_KMP";

// console.log(FindIndex_BF.find("abcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzzyx", "xyzzyx"));
// console.log(FindIndex_KR.find("abcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzzyx", "xyzzyx"));
console.log(FindIndex_KMP.find("abcdabcabcd", "abcabcd"));
