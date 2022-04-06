export class newcropdata{
    static farmerid: any;
  static farmername: any;
    
    constructor(
            public croplocation: string,
            public cropname: string,
            public cropprice: number,
            public cropqty: string,
            public croptype:string,
            public farmerid:number,
            public farmername:string
    ){}
}