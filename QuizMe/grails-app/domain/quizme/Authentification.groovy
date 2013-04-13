package quizme

import user.User



class Authentification {

    static constraints = {
    } 
	
	static boolean identification(){
		println "authen domain "
		println "2 authen domain " + User.count
		def list = User.all
		boolean ok = false
		list.each{
		//	println it.getUserId()
				
			
		}
		ok
	}
		
}
