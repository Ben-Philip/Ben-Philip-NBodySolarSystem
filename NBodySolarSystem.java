
/**
 * This program simulates the solar system, with N number of bodies orbiting the sun.
 * Remember to set up the following libraries (.jar files): algs4, introcs, stdlib
 *
 * @author (Philip Osuri Ben)
 * To test or run this program, type the following strings as the arguments:
 * "157788000.0", "25000.0", "planets.txt"
 * 
 */
public class NBodySolarSystem{

    public static void main(String[] args) {

        //args= {"157788000.0", "25000.0", "planets.txt" };

        double T = Double.parseDouble(args[0]); /*the total time which we type in as the first element of the string array, args.*/
        double dt = Double.parseDouble(args[1]); /*the change in time which we type in as the second element of the string array, args.*/
        In infile = new In(args[2]);

        //initailize variables for Positions, Velocities and Mass. 

        int N = infile.readInt();     /*reads in number of planets (bodies) from the text document called "planets.txt". 
        So, type in "planets.txt" as the third element of array args so the file planets.txt can be read.*/

        double R = infile.readDouble();    //reads in radius of universe 
        double[] Px = new double[N]  ;     //reads in and stores x-xoodinate of Positions 
        double[] Py = new double[N]  ;     //reads in and stores y-xoodinate of Positions
        double[] Vx = new double[N]  ;     //reads in and stores x-xoodinate of Velocities
        double[] Vy = new double[N]  ;     //reads in and stores y-xoodinate of Velocities
        double[] M = new double[N]   ;     //reads in and stores masses of planets 
        String[] image = new String[N];      //reads in and stores names of image file used to display the bodies (planets)

        StdDraw.setXscale(-R, R);   //We set the scale of the window using the radius.
        StdDraw.setYscale(-R, R);   //We set the scale of the window using the radius.
        /*using a for loop to read in the values (which are the elements of each array). 
        Note that these arrays read in the information from the text file "planets.txt" */
        for (int i=0; i< N; i++) {
            Px[i]   = infile.readDouble();
            Py[i]   = infile.readDouble();
            Vx[i]   = infile.readDouble();
            Vy[i]   = infile.readDouble(); 
            M[i]    = infile.readDouble();
            image[i]  = infile.readString();
        }

        /*For the entire duration specified by (T/dt), We create loop to run the entire block of codes controlling the animations.  
        StdAudio.play("./2001.wav");  This code plays audio. However, I commented it out because it refused to work*/ 
        for (int w=0; w< (T/dt); w++) {
            //Just initializing variables we need to calculate forces and to update the velocity and position arrays 
            StdDraw.enableDoubleBuffering();
            double[] Fx= new double[N];       
            double[] Fy= new double[N];
            double[] ax= new double[N];
            double[] ay= new double[N];
            double G = 6.67e-11; 
            //To calculate forces, we use the following for-loop-inside-another-for-loop (calles Nesting)
            for ( int i=0; i<N; i++){

                for (int j=0; j<N; j++){
                    if (i !=j){ 
                        double fx=0;     /**initalizing the x direction of the net force. 
                        Note we use lower case letters to avoid trouble with Fx declared in the bigger for loop "i.e mother of all our for loop"*/

                        double fy=0;     //initalizing the y direction of the net force. Again, we use lower case letters as the name of this variable.
                        double dx =  Px[j] -Px[i] ;//change in x positions
                        double dy =  Py[j] -Py[i] ;//change in y position
                        double r = Math.sqrt((dx*dx)+(dy*dy));//calculating the magnitude of radius
                        double F = (G*M[i]*M[j])/(r*r);  //calculating the force
                        fx = (F*dx)/r ;   //updating the value of x component of net force
                        fy = (F*dy)/r ;   //updating the value of y component of net force
                        Fx[i] +=  fx;     //Now, we are updating the net X component of the force array
                        Fy[i] +=  fy;     //Now, we are updating the net Y component of the force array
                    }   
                }
            }    

            //Time to apply newtons laws of motion in the following for loop to update the velocities and positions of the planets. 
            for (int i=0; i<N; i++){

                ax[i]=(Fx[i]/M[i]);
                ay[i]=(Fy[i]/M[i]);
                Vx[i] +=(ax[i] *dt); 
                Vy[i] +=(ay[i] *dt); 
                Px[i] +=(Vx[i] *dt);
                Py[i] +=(Vy[i] *dt);

            }  

            StdDraw.picture(0, 0, "starfield.jpg");// Remember the bluish background filled with stars? Well, here we put its image.

            //In order to plot the positions and motions of the bodies (planets), we use the following for-loop
            for (int p = 0; p<N; p++){

                StdDraw.picture(Px[p],Py[p] ,image[p]);

            } 
            StdDraw.show();
            StdDraw.pause(15); /*This pause also helps control the speed since the lesser the pause time in the agument, 
            the faster the bodies move and vice-versa. In fact, 0 pause time is too fast to be visible.*/

        }       
        //A little nap didn't hurt. I now end by printing out the final positions of the bodies. 
        StdOut.printf("%d\n", N);
        StdOut.printf("%.2e\n", R);
        for (int i = 0; i < N; i++) {
            StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",
                Px[i], Py[i], Vx[i], Vy[i], M[i], image[i]);
        }
    }
}