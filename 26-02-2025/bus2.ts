class Bus{
    make:string;
    model:string;
    year:number;
    constructor(make:string,model:string,year:number){
        this.make=make;
        this.model=model;
        this.year=year;
    }
    start():void{
        console.log(`The ${this.year} ${this.make} ${this.model} bus is starting.`);
    }
}
const myBus = new Bus("Volvo", "B9R", 2022);
myBus.start();