package encoder;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JFrame;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.awt.GLCanvas;

public class encoderJogl implements GLEventListener {

	static Scanner sn = new Scanner(System.in);
	static int choice;
	static int logic;
	static ArrayList<Integer> dataset=new ArrayList<>();
	public static void main(String[] args) {
		
		System.out.println("Enter your digital data for LINE CODING");
		String s=sn.nextLine();
		System.out.println("Select the logic you want");
		System.out.println("1. Positive Logic");
		System.out.println("2. Negative Logic");
		logic=sn.nextInt();
		System.out.println("Select the type of Line Coding you want");
		System.out.println("1. Unipolar");
		System.out.println("2. NRZ-L\n3. NRZ-I\n4. RZ");
		System.out.println("5. Manchester\n6. Differential Manchester");
		System.out.println("7. Alternate Mark Invrsion (AMI)\n8. Pseudoternary");
		System.out.println("9. Modified AMI (Scrambling)");
		choice=sn.nextInt();
		encodeData(s,choice);
		
		final GLProfile profile = GLProfile.get(GLProfile.GL2);
	      GLCapabilities capabilities = new GLCapabilities(profile);
	   
	      // The canvas
	      final GLCanvas glcanvas = new GLCanvas(capabilities);
	      encoderJogl l = new encoderJogl();
	      glcanvas.addGLEventListener(l);
	      
	      glcanvas.setSize(1500, 200);
	      JFrame.setDefaultLookAndFeelDecorated(true);
	      //creating frame
	      final JFrame frame = new JFrame ("straight Line");
	      frame.getContentPane().setBackground(new Color(0.0f,0.0f,1.0f));
	      //frame.setOpacity(0.8f);
	      //adding canvas to frame
	      
	      frame.getContentPane().add(glcanvas);
	      
	      frame.setSize(frame.getContentPane().getPreferredSize());
	      frame.setVisible(true);
		
		
	}

	public static void encodeData(String s,int choice) {
		
		switch(choice) {
		
		case 1:
			for(int i=0;i<s.length();i++) {
				dataset.add((int)s.charAt(i)-48);
			}
				        
			break;
			
		case 2:
			for(int i=0;i<s.length();i++) {
				if(s.charAt(i)=='0') {
					dataset.add(-1);
				}
				else {
					dataset.add(1);
				}
				
			}
			
			break;
			
		case 3:
			int prev;
			if(s.charAt(0)=='0') {
				dataset.add(-1);
				prev=-1;
			}
			else {
				dataset.add(1);
				prev=1;
			}
			
			for(int i=1;i<s.length();i++) {
				if(s.charAt(i)=='0') {
					dataset.add(prev);
				}
				else {
					if(prev==-1) {
						dataset.add(1);
						prev=1;
					}
					else {
						dataset.add(-1);
						prev=-1;
					}
				}
				
			}
			
			break;
		case 4:
			for(int i=0;i<s.length();i++) {
				if(s.charAt(i)=='0') {
					dataset.add(-1);
					dataset.add(0);
				}
				else {
					dataset.add(1);
					dataset.add(0);
				}
				
			}
			
			break;
		case 5:
			for(int i=0;i<s.length();i++) {
				if(s.charAt(i)=='0') {
					dataset.add(1);
					dataset.add(-1);
				}
				else {
					dataset.add(-1);
					dataset.add(1);
				}
				
			}
			
			break;
		case 6:
			int prev1;
			if(s.charAt(0)=='0') {
				dataset.add(1);
				dataset.add(-1);
				prev1=-1;
			}
			else {
				dataset.add(-1);
				dataset.add(1);
				prev1=1;
			}
			
			for(int i=1;i<s.length();i++) {
				if(s.charAt(i)=='1') {
					if(prev1==1) {
						dataset.add(1);
						dataset.add(-1);
						prev1=-1;
					}
					else {
						dataset.add(-1);
						dataset.add(1);
						prev1=1;
					}
				}
				else {
					if(prev1==1) {
						dataset.add(-1);
						dataset.add(1);
						prev1=1;
					}
					else {
						dataset.add(1);
						dataset.add(-1);
						prev1=-1;
					}
				}
				
			}
			
			break;
		case 7:
			int prev2=-1;
			for(int i=0;i<s.length();i++) {
				if(s.charAt(i)=='0') {
					dataset.add(0);
				}
				else {
					if(prev2==1) {
						dataset.add(-1);
						prev2=-1;
					}
					else {
						dataset.add(1);
						prev2=1;
					}
				}
			}
			
			break;
		case 8:
			int prev3=1;
			for(int i=0;i<s.length();i++) {
				if(s.charAt(i)=='1') {
					dataset.add(0);
				}
				else {
					if(prev3==1) {
						dataset.add(-1);
						prev3=-1;
					}
					else {
						dataset.add(1);
						prev3=1;
					}
				}
			}
			
			break;
		case 9:
			System.out.println("Choose the type of Scrambling you want");
			System.out.println("1. B8ZS (Bipolar with 8 zero subsitution)");
			System.out.println("2. HDB3 (High Density Bipolar 3 zero)");
			int ch=sn.nextInt();
			
			if(ch==1) {
				if(s.contains("00000000")) {
					s=s.replace("00000000", "000VB0VB");
					int prev4=-1;
					for(int i=0;i<s.length();i++) {
						if(s.charAt(i)=='0') {
							dataset.add(0);
						}
						else if(s.charAt(i)=='1'){
							if(prev4==1) {
								dataset.add(-1);
								prev4=-1;
							}
							else {
								dataset.add(1);
								prev4=1;
							}
						}
						else if(s.charAt(i)=='V') {
							if(prev4==1) {
								dataset.add(1);
								prev4=1;
							}
							else {
								dataset.add(-1);
								prev4=-1;
							}
						}
						else if(s.charAt(i)=='B') {
							if(prev4==1) {
								dataset.add(-1);
								prev4=-1;
							}
							else {
								dataset.add(1);
								prev4=1;
							}
						}
					}
				}
				
			}
			else if(ch==2) {
				int prev5=-1,count=0;
				for(int i=0;i<s.length();i++) {
					if(s.charAt(i)=='1') {
						count++;
						if(prev5==1) {
							dataset.add(-1);
							prev5=-1;
						}
						else {
							dataset.add(1);
							prev5=1;
						}
						
					}
					else {
						
						
						if(i+3<s.length()) {
							if(s.substring(i,i+4).equals("0000")) {
								if(count%2==0) {
									s=s.replaceFirst("0000", "B00V");
								}
								else {
									s=s.replaceFirst("0000", "000V");
								}
							}
						}	
						
						if(s.charAt(i)=='B') {
							if(prev5==1) {
								dataset.add(-1);
								prev5=-1;
							}
							else {
								dataset.add(1);
								prev5=1;
							}
							count++;
						}
						else if(s.charAt(i)=='V') {
							if(prev5==1) {
								dataset.add(1);
								prev5=1;
							}
							else {
								dataset.add(-1);
								prev5=-1;
							}
							count++;
						}
						else {
							dataset.add(0);
						}
						
					}
				}
				
			}
			
			break;
		default:
			System.out.println("Wrong Choice");
			break;
	}
	}

	@Override
	public void display(GLAutoDrawable arg0) {
		// TODO Auto-generated method stub
		final GL2 gl = arg0.getGL().getGL2();
	      
	      for(float i=-1.0f;i<=1.0f;i=i+0.5f) {
	    	  gl.glBegin (GL2.GL_LINES);//static field
	    	  //gl.glColor3f(0.0f, 0.0f, 0.0f);
	          gl.glVertex3f(-1.0f,i,0);
	          gl.glVertex3f(1.0f,i,0);
	          gl.glEnd();
	      }
	      for(float i=-1.0f;i<=1.0f;i=i+0.1f) {
	    	  gl.glBegin (GL2.GL_LINES);//static field
	    	  //gl.glColor3f(0.0f, 0.0f, 0.0f);
	          gl.glVertex3f(i,-1.0f,0);
	          gl.glVertex3f(i,1.0f,0);
	          gl.glEnd();
	      }
	      if(logic == 2) {
	    	  ArrayList<Integer> temp = new ArrayList<>();
	    	  for(int i=0;i<dataset.size();i++) {
	    		  int zeroOne = dataset.get(i);
	    		  if(zeroOne == 0) {
	    			  temp.add(0);
	    		  }
	    		  else if (zeroOne == 1){
	    			  temp.add(-1);
	    		  }
	    		  else if(zeroOne == -1) {
	    			  temp.add(1);
	    		  }
	    	  }
	    	  dataset=temp;
	      }
	      
	      if (choice==4 || choice==5 || choice==6) {
	    	  float v=-1.0f;
		      for(int i=0;i<dataset.size();i++) {
		    	  gl.glHint(gl.GL_LINE_SMOOTH_HINT,gl.GL_NICEST);
		    	  gl.glEnable(gl.GL_LINE_SMOOTH);
		    	  gl.glLineWidth(4);
		    	  gl.glBegin (GL2.GL_LINES);//static field
		    	  gl.glColor3f(0.0f, 1.0f, 0.0f);
		    	  
		          gl.glVertex3f(dataset.get(i)/10+v,(float) ((float)dataset.get(i)/2.0),0);
		          gl.glVertex3f(dataset.get(i)/10+v+0.05f,(float) ((float)dataset.get(i)/2.0),0);
		          gl.glEnd();
		          v+=0.05f;
		      }
		      v=-1.0f;
		      for(int i=0;i<dataset.size()-1;i++) {
		    	  gl.glBegin (GL2.GL_LINES);//static field
		    	  gl.glColor3f(0.0f, 1.0f, 0.0f);
		    	  
		          gl.glVertex3f(dataset.get(i)/10+v+0.05f,(float) ((float)dataset.get(i)/2.0),0);
		          gl.glVertex3f(dataset.get(i)/10+v+0.05f,(float) ((float)dataset.get(i+1)/2.0),0);
		          gl.glEnd();
		          v+=0.05f;
		      }
	      }
	      else {
	    	  float v=-1.0f;
		      for(int i=0;i<dataset.size();i++) {
		    	  gl.glHint(gl.GL_LINE_SMOOTH_HINT,gl.GL_NICEST);
		    	  gl.glEnable(gl.GL_LINE_SMOOTH);
		    	  gl.glLineWidth(6);
		    	  gl.glBegin (GL2.GL_LINES);//static field
		    	  gl.glColor3f(0.0f, 1.0f, 0.0f);
		    	  
		          gl.glVertex3f(dataset.get(i)/10+v,(float) ((float)dataset.get(i)/2.0),0);
		          gl.glVertex3f(dataset.get(i)/10+v+0.1f,(float) ((float)dataset.get(i)/2.0),0);
		          gl.glEnd();
		          v+=0.1f;
		      }
		      v=-1.0f;
		      for(int i=0;i<dataset.size()-1;i++) {
		    	  gl.glBegin (GL2.GL_LINES);//static field
		    	  gl.glColor3f(0.0f, 1.0f, 0.0f);
		    	  
		          gl.glVertex3f(dataset.get(i)/10+v+0.1f,(float) ((float)dataset.get(i)/2.0),0);
		          gl.glVertex3f(dataset.get(i)/10+v+0.1f,(float) ((float)dataset.get(i+1)/2.0),0);
		          gl.glEnd();
		          v+=0.1f;
		      }
	      }
	      
	}

	@Override
	public void dispose(GLAutoDrawable arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void init(GLAutoDrawable arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void reshape(GLAutoDrawable arg0, int arg1, int arg2, int arg3, int arg4) {
		// TODO Auto-generated method stub
		
	}

}
