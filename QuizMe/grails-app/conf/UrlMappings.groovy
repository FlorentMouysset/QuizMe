class UrlMappings {

	static mappings = {
		"/$controller/$action?/$id?"{
			constraints {
				// apply constraints here
			}
		}

 		println "ici urlmap" 
		
		"/AuthentificationController/identification" {
			println "ici urlmap2"
			controller = "AuthentificationController"
			action = "index"
		}
		
		"/"(view:"/index")
		"500"(view:'/error')
		
		
	}
}
