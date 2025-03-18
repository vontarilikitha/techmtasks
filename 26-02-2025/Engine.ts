class Engine{
    horsepower:number;
    fueltype:string
    constructor(horsepower :number,fueltype:string){
        this.horsepower=horsepower;
        this.fueltype=fueltype;
    }
    displayEnginedetails():void{
        console.log(`Engine: ${this.horsepower} HP, ${this.fueltype}`);
    }
}
class car{
    make:string;
    model:string;
    year:number;
    engine:Engine;
    constructor(make:string,model:string,year:number,engine:Engine){
        this.make=make;
        this.model=model;
        this.year=year;
        this.engine=engine;

    }
    start():void{
        console.log(`The ${this.year} ${this.make} ${this.model} is starting.`);

    }
    printcar():void{
        console.log(`Car: ${this.year} ${this.make} ${this.model}`);
        this.engine.displayEnginedetails();
    }
}
const myEngine = new Engine(250, "Gasoline");
const myCar = new car("Toyota", "Camry", 2022, myEngine);
myCar.start();
myCar.printcar();
