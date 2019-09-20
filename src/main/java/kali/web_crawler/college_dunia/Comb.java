package kali.web_crawler.college_dunia;

public class Comb {

	public static void main(String[] args) {
		int count=0;
		for(int n=0;n<=15;n++){
			for(int x=0;x<=15;x++){
				for(int y=0;y<=15;y++){
					for(int z=0;z<=15;z++){
							int ans= n+x+y+z;
							if(ans==15){
								System.out.println(n+", "+x+", "+y+", "+z);
								count++;
							}
					}
				}
			}
		}
		System.out.println(count);
	}
}
