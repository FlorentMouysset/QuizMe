package quizme

import user.Etudiant
import user.Professeur
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
		def adamId = creatSaveUserId("azer","azer")
		def adam = new Etudiant(nom:"Green",prenom:"Adam",	sexe:"M",	dateDeNaissance: new Date("11/11/1985"),email:"adam@green.net",	numEtudiant:"20805012",identifiants:adamId)

		if(!adam.save()) {
			adam.errors.allErrors.each{error ->
				println "An error occured with adam : "+error}
		}
		
		def profId = creatSaveUserId("prof","prof")
		User prof = new Professeur(prenom:"Prof",
			nom:"Prof",
			email:"prof@green.com",
			sexe:"M",
			dateDeNaissance: new Date("05/01/1958"),
			identifiants: profId)
			
		if(!prof.save()) {
			prof.errors.allErrors.each{error ->
				println "An error occured with prof : "+error}
		}

		
	}
	
	static def identification(String login , String mdp){
		clo()		
		
		println "authen domain nb users :" + User.count
	//	println "== " + User.findById(1)
	//	println "--" + Etudiant.findById(1)
		def list = User.all
		//println User.all
		def find = null
		
		def it = list.iterator()
		//println "#" +it.hasNext() 
		while(it.hasNext() && find==null ){
			def user = it.next()
			//println "##" + user.getIdentifiants().getLogin()  + " " + login + " " + user.getIdentifiants().getPassword() +" "+  mdp
			if (user.getIdentifiants().getLogin() == login &&  user.getIdentifiants().getPassword() == mdp){
				println "find !"
				find = user
			} 
		}
		find
	}



}
