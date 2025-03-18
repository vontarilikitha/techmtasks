let str="digiterat"
let res=str;
let rotations=[];
for(let i=0;i<str.length;i++){
  const ch=res.slice(-1);
  res=ch+res.slice(0,-1);
  rotations.push(res);

}
console.log(rotations);
