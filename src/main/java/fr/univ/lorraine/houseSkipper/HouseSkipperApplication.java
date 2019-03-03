package fr.univ.lorraine.houseSkipper;

import fr.univ.lorraine.houseSkipper.configuration.FileStorageProperties;
import fr.univ.lorraine.houseSkipper.model.Phase;
import fr.univ.lorraine.houseSkipper.model.SubPhase;
import fr.univ.lorraine.houseSkipper.repositories.PhaseRepository;
import fr.univ.lorraine.houseSkipper.repositories.SubPhaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
@EnableConfigurationProperties({
		FileStorageProperties.class
})
@EnableWebSecurity
public class HouseSkipperApplication {

	@Autowired
	private PhaseRepository phaseRepository;

	@Autowired
	private SubPhaseRepository subPhaseRepository;



	@Bean
	public void initDB(){
		try {
			if(phaseRepository.findAll().size() == 0 || subPhaseRepository.findAll().size() == 0)
			{
				createDB();
			}
		}catch (Exception e){

			e.printStackTrace();
		}
	}

	public void createDB(){
		phaseRepository.save(new Phase(new Long(1), "Redaction"));
		phaseRepository.save(new Phase(new Long(2), "Validation"));
		phaseRepository.save(new Phase(new Long(3), "Soumission"));
		phaseRepository.save(new Phase(new Long(4), "Evaluation"));
		phaseRepository.save(new Phase(new Long(5), "Decision"));
		phaseRepository.save(new Phase(new Long(6), "Finalisation"));
		phaseRepository.save(new Phase(new Long(7), "Exploitation"));

		for (Phase p: phaseRepository.findAll()) {
			switch (p.getPhaseName()){
				case "Redaction":
					SubPhase s1 = new SubPhase((long)1,"Description en cours");
					SubPhase s2 = new SubPhase((long)2,"Description finalisée");
					List<SubPhase> sub = new ArrayList<>();
					sub.add(s1);
					sub.add(s2);
					Phase ph = phaseRepository.findByPhaseName(p.getPhaseName());
					ph.setSubPhase(sub);
					s1.setPhase(ph);
					s2.setPhase(ph);
					subPhaseRepository.save(s1);
					subPhaseRepository.save(s2);
					phaseRepository.save(ph);
					break;
				case "Validation":
					SubPhase s3 = new SubPhase((long)3,"Description en cours de relecture");
					SubPhase s4 = new SubPhase((long)4,"Description à compléter");
					SubPhase s5 = new SubPhase((long)5,"Description à validée");
					List<SubPhase> sub1 = new ArrayList<>();
					sub1.add(s3);
					sub1.add(s4);
					sub1.add(s5);
					Phase ph1 = phaseRepository.findByPhaseName(p.getPhaseName());
					ph1.setSubPhase(sub1);
					s3.setPhase(ph1);
					s4.setPhase(ph1);
					s5.setPhase(ph1);
					subPhaseRepository.save(s3);
					subPhaseRepository.save(s4);
					subPhaseRepository.save(s5);
					phaseRepository.save(ph1);
					break;
				case "Soumission":
					SubPhase s6 = new SubPhase((long)6,"En attente de réponse");
					SubPhase s7 = new SubPhase((long)7,"Réponses en cours");
					SubPhase s8 = new SubPhase((long)8,"Réponses clôturées");
					List<SubPhase> sub2 = new ArrayList<>();
					sub2.add(s6);
					sub2.add(s7);
					sub2.add(s8);
					Phase ph2 = phaseRepository.findByPhaseName(p.getPhaseName());
					ph2.setSubPhase(sub2);
					s6.setPhase(ph2);
					s7.setPhase(ph2);
					s8.setPhase(ph2);
					subPhaseRepository.save(s6);
					subPhaseRepository.save(s7);
					subPhaseRepository.save(s8);
					phaseRepository.save(ph2);
					break;
				case "Evaluation":
					SubPhase s9 = new SubPhase((long)9,"Description en cours");
					SubPhase s10 = new SubPhase((long)10,"Description finalisée");
					List<SubPhase> sub3 = new ArrayList<>();
					sub3.add(s9);
					sub3.add(s10);
					Phase ph3 = phaseRepository.findByPhaseName(p.getPhaseName());
					ph3.setSubPhase(sub3);
					s9.setPhase(ph3);
					s10.setPhase(ph3);
					subPhaseRepository.save(s9);
					subPhaseRepository.save(s10);
					phaseRepository.save(ph3);
					break;
				case "Decision":
					SubPhase s11 = new SubPhase((long)11,"Abandon");
					SubPhase s12 = new SubPhase((long)12,"Suspendu");
					SubPhase s13 = new SubPhase((long)13,"Réalisation");
					List<SubPhase> sub4 = new ArrayList<>();
					sub4.add(s11);
					sub4.add(s12);
					sub4.add(s13);
					Phase ph4 = phaseRepository.findByPhaseName(p.getPhaseName());
					ph4.setSubPhase(sub4);
					s11.setPhase(ph4);
					s12.setPhase(ph4);
					s13.setPhase(ph4);
					subPhaseRepository.save(s11);
					subPhaseRepository.save(s12);
					subPhaseRepository.save(s13);
					phaseRepository.save(ph4);
					break;
				case "Finalisation":
					SubPhase s14 = new SubPhase((long)14,"Vérification en cours");
					SubPhase s15 = new SubPhase((long)15,"En attente de complément");
					SubPhase s16 = new SubPhase((long)16,"Vérification finalisée");
					SubPhase s17 = new SubPhase((long)17,"En attente d’exploitation");
					List<SubPhase> sub5 = new ArrayList<>();
					sub5.add(s14);
					sub5.add(s15);
					sub5.add(s16);
					sub5.add(s17);
					Phase ph5 = phaseRepository.findByPhaseName(p.getPhaseName());
					ph5.setSubPhase(sub5);
					s14.setPhase(ph5);
					s15.setPhase(ph5);
					s16.setPhase(ph5);
					s17.setPhase(ph5);
					subPhaseRepository.save(s17);
					subPhaseRepository.save(s14);
					subPhaseRepository.save(s15);
					subPhaseRepository.save(s16);
					phaseRepository.save(ph5);
					break;
				case "Exploitation":
					SubPhase s18 = new SubPhase((long)18,"En attente d’exploitation");
					SubPhase s19 = new SubPhase((long)19,"En cours d’exploitation");
					List<SubPhase> sub6 = new ArrayList<>();
					sub6.add(s18);
					sub6.add(s19);
					Phase ph6 = phaseRepository.findByPhaseName(p.getPhaseName());
					ph6.setSubPhase(sub6);
					s18.setPhase(ph6);
					s19.setPhase(ph6);
					subPhaseRepository.save(s18);
					subPhaseRepository.save(s19);
					phaseRepository.save(ph6);
					break;
			}

		}
}

	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	public static void main(String[] args) {
		SpringApplication.run(HouseSkipperApplication.class, args);

	}



}
