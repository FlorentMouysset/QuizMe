import user.Etudiant
import user.Professeur
import user.User
import user.UserId

class BootStrap {

    def init = { servletContext ->
		User adam = new Etudiant(nom:"Green",
			prenom:"Adam",
			sexe:"M",
			dateDeNaissance: new Date("11/11/1985"),
			email:"adam@green.net",
			identifiants:new UserId(login:"adam", password:"adam"),
			numEtudiant:"20805012")
		
		if(!adam.save()) {
			adam.errors.allErrors.each{error ->
				println "An error occured with adam : "+error}
		}
			
			
		User eve = new Etudiant(prenom:"Eve",
			nom:"Green",
			email:"eve@green.net",
			sexe:"F",
			dateDeNaissance: new Date("02/04/1986"),
			numEtudiant:"20804023",
			identifiants: new UserId(login:"eveF", password:"eveF"))
			
		if(!eve.save()) {
			eve.errors.allErrors.each{error ->
				println "An error occured with eve : "+error}
		}
		
		User prof = new Professeur(prenom:"Prof",
			nom:"Prof",
			email:"prof@gmail.com",
			sexe:"M",
			dateDeNaissance: new Date("05/01/1958"),
			identifiants: new UserId(login:"prof", password:"prof"))
			
		if(!prof.save()) {
			prof.errors.allErrors.each{error ->
				println "An error occured with prof : "+error}
		}
		
    }
    def destroy = {
    }
}
