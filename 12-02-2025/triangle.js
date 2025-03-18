let a=prompt();
let b=prompt();
let c=prompt();
console.log(area(a,b,c))
function area(a,b,c){
    const s=(a+b+c)/2;
    const res=Math.sqrt(s*(s-a)*(s-b)*(s-c));
    return res;
}
