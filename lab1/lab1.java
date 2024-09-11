public class Lab1{

    public static void print_lab1(){
        int[] a;
        double[] x;
        double[][] c;
        a = new int[10];

        for(int index = 0; index < a.length; ++index) a[index] = 24 - 2*index;

        x = new double[11];
        /* Math.random() generates random double from 0.0 to 1.0
         * If I multiply Math.random() by 13 it will generate numbers from 0.0 to 13.0
         * If I subtract 11 it will generate numbers from -11.0 to 2.0
         */
        for(int index = 0; index < x.length; ++index) x[index] = Math.random() * 13 - 11;

        c = new double[10][11];

        for(int i = 0; i < c.length; ++i)
            for(int j = 0; j < c[i].length; ++j)
                switch (a[i]){
                    case 16: 
                        c[i][j] = (Math.log(Math.pow(Math.E,x[j])) - 0.25)/(Math.log(Math.pow(Math.E,x[j]))); 
                        break;
                    case 8:
                    case 18:
                    case 20:
                    case 22:
                    case 24:
                        c[i][j] = Math.pow((Math.PI * (Math.sin(x[j])+1)/3),Math.pow(0.25/(Math.tan(x[j])),3));
                        break;
                    default:
                        c[i][j] = Math.cos(Math.pow(Math.E,Math.pow((2/x[j]),Math.pow(3,((Math.tan(x[j])+1)/2)))));
                        break;
                }
        for(int i = 0; i < c.length; ++i){
            for(int j = 0; j < c[i].length; ++j){
                System.out.printf(" " + "%7.4f", c[i][j]);
            }

            System.out.println();
            
            }
    }

    
    public static void main(String[] args){
        print_lab1();
    }
    
}