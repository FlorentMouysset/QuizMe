import user.Etudiant
import user.Professeur
import user.User
import user.UserId

class BootStrap {


	def creatSaveUserId = {login, mdp->
		def idUser = new UserId(login:login, password:mdp)
			
		if( !idUser.save() ) {
			idUser.errors.allErrors.each{error ->
					println "An error occured with id : "+error}
		}
		idUser
	}
	
	
		
    def init = { servletContext ->
		
		def adamId = creatSaveUserId("adamId","adamMdp")
		def adam = new Etudiant(nom:"Green",
			prenom:"Adam",
			sexe:"M",
			dateDeNaissance: new Date("11/11/1985"),
			email:"adam@gmail.com",
			numEtudiant:"20805012"
			,identifiants:adamId)
		
		if(!adam.save()) {
			adam.errors.allErrors.each{error ->
				println "An error occured with adam : "+error}
		}
			
		def eveId = creatSaveUserId("eveId","eveMpd")
		User eve = new Etudiant(prenom:"Eve",
			nom:"Green",
			email:"eve@gmail.com",
			sexe:"F",
			dateDeNaissance: new Date("02/04/1986"),
			numEtudiant:"20804023",
			identifiants: eveId)
			
		if(!eve.save()) {
			eve.errors.allErrors.each{error ->
				println "An error occured with eve : "+error}
		}
		
		def profId = creatSaveUserId("profId","profMdp")
		User prof = new Professeur(prenom:"Prof",
			nom:"Prof",
			email:"prof@gmail.com",
			sexe:"M",
			dateDeNaissance: new Date("05/01/1958"),
			identifiants: profId)
			
		if(!prof.save()) {
			prof.errors.allErrors.each{error ->
				println "An error occured with prof : "+error}
		}
		
    }
    def destroy = {
    }
}
