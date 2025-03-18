function date_time(){
    const days=["Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"]
    const d=new Date();
   console.log (days[d.getDay()]);
    const hours=d.getHours();
    const min=d.getMinutes();
    const sec=d.getSeconds();
    const ampm= hours>=12?"PM":"AM";
    const tw=hours%12 || 12;
    console.log(tw+" "+ampm+" : " +min +": "+sec);
}
date_time();