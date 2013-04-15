package quizme

import user.Etudiant
import user.User
import user.UserId


class Authentification {

	static constraints = {
	}
	
	static def creatSaveUserId = {login, mdp->
		def idUser = new UserId(login:login, password:mdp)

		if( !idUser.save() ) {
			idUser.errors.allErrors.each{error ->
				println "An error occured with id : "+error}
		}
		idUser
	}

	static clo(){
		def adamId = creatSaveUserId("adamId","adamMdp")
		def adam = new Etudiant(nom:"Green",prenom:"Adam",	sexe:"M",	dateDeNaissance: new Date("11/11/1985"),email:"adam@green.net",	numEtudiant:"20805012",identifiants:adamId)

		if(!adam.save()) {
			adam.errors.allErrors.each{error ->
				println "An error occured with adam : "+error}
		}
	}
	
	static boolean identification(String login , String mdp){
		clo()
		
		println "authen domain nb users :" + User.count
		def list = User.all
		println User.all
		boolean find = false
		
		def it = list.iterator()
		println "#" +it.hasNext() 
		while(it.hasNext() && !find ){
			def user = it.next()
			println "##" + user.getIdentifiants().getLogin()  + " " + login + " " + user.getIdentifiants().getPassword() +" "+  mdp
			if (user.getIdentifiants().getLogin() == login &&  user.getIdentifiants().getPassword() == mdp){
				println "find !"
				find = true
			} 
		}
		find
	}



}
