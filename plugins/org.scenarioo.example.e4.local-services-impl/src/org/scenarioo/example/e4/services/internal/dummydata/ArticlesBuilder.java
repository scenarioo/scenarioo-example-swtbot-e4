/* Copyright (c) 2014, scenarioo.org Development Team
 * All rights reserved.
 *
 * See https://github.com/scenarioo?tab=members
 * for a complete list of contributors to this project.
 *
 * Redistribution and use of the Scenarioo Examples in source and binary forms,
 * with or without modification, are permitted provided that the following
 * conditions are met:
 *
 * * Redistributions of source code must retain the above copyright notice, this
 *   list of conditions and the following disclaimer.
 *
 * * Redistributions in binary form must reproduce the above copyright notice, this
 *   list of conditions and the following disclaimer in the documentation and/or
 *   other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 * ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.scenarioo.example.e4.services.internal.dummydata;

import org.scenarioo.example.e4.domain.Article;
import org.scenarioo.example.e4.domain.Unit;
import org.scenarioo.example.e4.services.internal.ArticleImages;
import org.scenarioo.example.e4.services.internal.Counter;

public class ArticlesBuilder {

	private static final ArticlesBuilder INSTANCE = new ArticlesBuilder();

	private final Counter counter;

	private ArticlesBuilder() {
		this.counter = Counter.getInstance();
	}

	public static ArticlesBuilder getInstance() {
		return INSTANCE;
	}

	public Article createBobaFettArticle() {
		Article article = new Article();
		article.generateAndSetId(counter);
		article.setArticleNumber("Boba Fett");
		article.setDescription("Bounty hunter, His aura of danger"
				+ " and mystery have created a cult following for the character");
		article.setImageDescriptor(ArticleImages.BOBA_FETT.getImageDescriptor());
		article.setUnit(Unit.PIECE);
		return article;
	}

	public Article createAaylaSecuraArticle() {
		Article article = new Article();
		article.generateAndSetId(counter);
		article.setArticleNumber("Aayla Secura");
		article.setDescription("Twi'lek Jedi, who appears in Attack of the Clones, "
				+ "Revenge of the Sith, Dark Horse Comics' Republic and Clone Wars series, "
				+ "and The Clone Wars television series");
		article.setImageDescriptor(ArticleImages.AAYLA_SECURA.getImageDescriptor());
		article.setUnit(Unit.PIECE);
		return article;
	}

	public Article createAckbarArticle() {
		Article article = new Article();
		article.generateAndSetId(counter);
		article.setArticleNumber("Admiral Gial Ackbar");
		article.setDescription("Supreme Commander of the Rebel Alliance Fleet.\\n\\nGial Ackbar was a male Mon Calamari who became the foremost"
				+ " military commander of the Alliance to Restore the Republic and its successor government the New Republic. During the Clone Wars,"
				+ " he was in charge of the Mon Calamari Guard and fought in the battle that had secured Prince Lee-Char's way to the throne as King"
				+ " of Dac. Having previously been an Imperial slave to Grand Moff Wilhuff Tarkin, he was freed by Captain Juno Eclipse and was"
				+ " recruited to join the Rebellion. Later, he fought alongside Renegade Squadron and was once captured by bounty hunter Boba Fett."
				+ " During the Battle of Endor, later revealed to be a trap for him and the Alliance, he led Alliance naval forces against the Death"
				+ " Star II. He held the distinction of having been the Supreme Commander of the New Republic Defense Force for nearly two decades and"
				+ " defeating two Imperial Grand Admirals, Osvald Teshik and Peccati Syn, along with numerous other threats. He wrote the manual for"
				+ " the New Republic Fleet Academy, titled Fleet Tactics and Combat Methodology.\\n\\nAfter the Pellaeon�Gavrisom Treaty, Ackbar"
				+ " retired to a quiet life of writing his memoirs and advising. However, although old and infirm, he would plan the decisive Battle"
				+ " of Ebaq 9 that brought about the end of Tsavong Lah and much of the Yuuzhan Vong fleet. He would die of old age in 29 ABY and"
				+ " be remembered as a great military leader.");
		article.setImageDescriptor(ArticleImages.ACKBAR.getImageDescriptor());
		article.setUnit(Unit.PIECE);
		return article;
	}

	public Article createDarthVaderArticle() {
		Article article = new Article();
		article.generateAndSetId(counter);
		article.setArticleNumber("Darth Vader");
		article.setDescription("Supreme Commander of the Imperial Fleet");
		article.setImageDescriptor(ArticleImages.VADER.getImageDescriptor());
		article.setUnit(Unit.PIECE);
		return article;
	}

	public Article createAnakinSkywalkerArticle() {
		Article article = new Article();
		article.generateAndSetId(counter);
		article.setArticleNumber("Anakin Skywalker");
		article.setDescription("Jedi Knight, General in the Grand Army of the Republic");
		article.setImageDescriptor(ArticleImages.ANAKIN_JEDI.getImageDescriptor());
		article.setUnit(Unit.PIECE);
		return article;
	}

	public Article createYodaArticle() {
		Article article = new Article();
		article.generateAndSetId(counter);
		article.setArticleNumber("Yoda");
		article.setDescription("Jedi Grand Master of the Order, Jedi Master of the High Council");
		article.setImageDescriptor(ArticleImages.YODA.getImageDescriptor());
		article.setUnit(Unit.PIECE);
		return article;
	}

	public Article createLukeSkywalkerArticle() {
		Article article = new Article();
		article.generateAndSetId(counter);
		article.setArticleNumber("Luke Skywalker");
		article.setDescription("Jedi Knight, Jedi Master, Grand Master of the New Jedi Order");
		article.setImageDescriptor(ArticleImages.VADER.getImageDescriptor());
		article.setUnit(Unit.PIECE);
		return article;
	}

	public Article createHanSoloArticle() {
		Article article = new Article();
		article.generateAndSetId(counter);
		article.setArticleNumber("Han Solo");
		article.setDescription("Captain of the Millennium Falcon, General in the Rebel Alliance/New Republic smuggler");
		article.setImageDescriptor(ArticleImages.HAN_SOLO.getImageDescriptor());
		article.setUnit(Unit.PIECE);
		return article;
	}

	public Article createObiWanKenobiArticle() {
		Article article = new Article();
		article.generateAndSetId(counter);
		article.setArticleNumber("Obi-Wan Kenobi");
		article.setDescription("Jedi Padawan, Jedi Knight, Jedi Master, Jedi Council Member, Jedi General");
		article.setImageDescriptor(ArticleImages.MASTER_OBI_WAN_KENOBI.getImageDescriptor());
		article.setUnit(Unit.PIECE);
		return article;
	}

	public Article createAhsokaTanoArticle() {
		Article article = new Article();
		article.generateAndSetId(counter);
		article.setArticleNumber("Ahsoka Tano");
		article.setDescription("Jedi Padawan (assigned to Anakin Skywalker),"
				+ " commander (Grand Army of the Republic), Rebel informant");
		article.setImageDescriptor(ArticleImages.AHSOKA_TANO.getImageDescriptor());
		article.setUnit(Unit.PIECE);
		return article;
	}

	/**
	 * @return
	 */
	public Article createBossNassArticle() {
		Article article = new Article();
		article.generateAndSetId(counter);
		article.setArticleNumber("Boss Rugor Nass");
		article.setDescription("Gungan leader in The Phantom Menace, and attends Padmé Amidala's funeral in Revenge of the Sith");
		article.setImageDescriptor(ArticleImages.BOSS_NASS.getImageDescriptor());
		article.setUnit(Unit.PIECE);
		return article;
	}

	/**
	 * @return
	 */
	public Article createChewbaccaArticle() {
		Article article = new Article();
		article.generateAndSetId(counter);
		article.setArticleNumber("Chewbacca");
		article.setDescription("First Mate on Millennium Falcon");
		article.setImageDescriptor(ArticleImages.CHEWBACCA.getImageDescriptor());
		article.setUnit(Unit.PIECE);
		return article;
	}

	/**
	 * @return
	 */
	public Article createGeneralTarpalsArticle() {
		Article article = new Article();
		article.generateAndSetId(counter);
		article.setArticleNumber("Roos Tarpal");
		article.setDescription("captain in the Gungan society of Otoh Gunga and one of the leaders in the Gungan Grand Army");
		article.setImageDescriptor(ArticleImages.GENERAL_TARPALS.getImageDescriptor());
		article.setUnit(Unit.PIECE);
		return article;
	}

	/**
	 * @return
	 */
	public Article createGreedoArticle() {
		Article article = new Article();
		article.generateAndSetId(counter);
		article.setArticleNumber("Greedo");
		article.setDescription("son of Greedo the Elder, was a male Rodian bounty hunter. He lived in Mos Espa alongside the young"
				+ " Anakin Skywalker and W. Wald circa 32 BBY. Although his father had been an esteemed hunter, and the chief rival"
				+ " of Navik the Red, the younger Greedo had little of his father's prowess and was easily killed by"
				+ " Han Solo in Chalmun's Spaceport Cantina on Tatooine.");
		article.setImageDescriptor(ArticleImages.GREEDO.getImageDescriptor());
		article.setUnit(Unit.PIECE);
		return article;
	}

	public Article createImperialProbeDroidArticle() {
		Article article = new Article();
		article.generateAndSetId(counter);
		article.setArticleNumber("Imperial Probe Droid");
		article.setDescription("Probe droids (also known as probots or recon droids) were droids that were specifically designed and"
				+ " programmed to perform reconnaissance duties. These units were often dispatched by various space navies to gather"
				+ " information from a variety of different locations, reporting anything of significance as defined by specific"
				+ " preprogrammed protocols. Some models were equipped with a self-destruct mechanism in order to prevent those"
				+ " whom it had been sent to observe from studying them and determining their source, as well as a small blaster-type weapon for defense");
		article.setImageDescriptor(ArticleImages.IMPERIAL_PROPE_DROID.getImageDescriptor());
		article.setUnit(Unit.PIECE);
		return article;
	}

	/**
	 * @return
	 */
	public Article createJarJarBinksArticle() {
		Article article = new Article();
		article.generateAndSetId(counter);
		article.setArticleNumber("Jar Jar Binks");
		article.setDescription("General in the Gungan Grand Army, Representative of the Gungan race, Senator of Chommell Sector (substituting for Padmé Amidala)");
		article.setImageDescriptor(ArticleImages.JAR_JAR_BINKS.getImageDescriptor());
		article.setUnit(Unit.PIECE);
		return article;
	}

	/**
	 * @return
	 */
	public Article createJabbaTheHuttArticle() {
		Article article = new Article();
		article.generateAndSetId(counter);
		article.setArticleNumber("Jabba the Hutt");
		article.setDescription("Jabba the Hutt was a Hutt gangster and crime lord who operated from his palace on the Outer Rim world of Tatooine."
				+ " The father to Rotta, Jabba was a major figure on Tatooine, where he controlled the bulk of the trafficking in illegal goods,"
				+ " piracy and slavery that generated most of the planet's wealth; in addition, he controlled the spaceports and settlements on the planet.");
		article.setImageDescriptor(ArticleImages.JABBA_THE_HUTT.getImageDescriptor());
		article.setUnit(Unit.PIECE);
		return article;
	}

	/**
	 * @return
	 */
	public Article createJawasArticle() {
		Article article = new Article();
		article.generateAndSetId(counter);
		article.setArticleNumber("Jawas");
		article.setDescription("Jawas were typically short rodent-like natives of Tatooine. They were passionate scavengers, seeking out technology for"
				+ " sale or trade in the deep deserts in their huge sandcrawler transports. A band of Jawas was responsible for locating C-3PO and R2-D2"
				+ " and selling them to Luke Skywalker's uncle, Owen Lars. Another tribe of Jawas, led by Tteel Kkak, found Jabba the Hutt's rancor."
				+ " They had a reputation for swindling, as they had a penchant for selling old equipment such as outdated faulty droids to moisture farmers."
				+ " However, they were extremely passive beings, and hardly put up any resistance to colonists of their planet unlike the other natives the Sand people,"
				+ " instead seeing foreigners as an excellent business opportunity.");
		article.setImageDescriptor(ArticleImages.JAWAS.getImageDescriptor());
		article.setUnit(Unit.PIECE);
		return article;
	}

	/**
	 * @return
	 */
	public Article createKitFistoArticle() {
		Article article = new Article();
		article.generateAndSetId(counter);
		article.setArticleNumber("Kit Fisto");
		article.setDescription("Kit Fisto was a renowned male Nautolan Jedi Master in the waning years of the Galactic Republic. In 41 BBY he took Bant Eerin as his Padawan."
				+ " He later trained Nahdar Vebb as his Padawan to knighthood as well. During the Clone Wars, he served as a Jedi General in the Grand Army of the Republic,"
				+ " as well as a member of the Jedi High Council. Fisto participated in and survived the Battle of Geonosis and led a team in the assault during the Battle of Mon Calamari."
				+ " Fisto was also present at Kamino, where he pushed the limits of the Jedi Code with his relationship with fellow Jedi Aayla Secura, whose life he saved during the battle."
				+ " On a mission to recapture Trade Federation Viceroy Nute Gunray, Fisto came face to face with General Grievous, nearly defeating the Jedi killer. However,"
				+ " the tide of the battle changed when Grievous activated his personal droid bodyguards and then slew Fisto's newly Knighted former Padawan, Nahdar Vebb."
				+ " At the height of the Clone Wars, he assisted Obi-Wan Kenobi on Ord Cestus and subsequently dueled with Asajj Ventress in order to stop the manufacturing of the Jedi Killer droids."
				+ " Finally, after years of being considered for a seat on the Jedi Council, Fisto was appointed to the august body due to his exemplary achievements during the Clone Wars."
				+ " As the war continued, Fisto was appointed to the Jedi Council. He was later dispatched with Obi-Wan Kenobi and Anakin Skywalker to Cato Neimoidia on a mission to capture Nute Gunray,"
				+ " who barely escaped the encounter with the three famous Jedi. During the Battle of Coruscant, Fisto aided in the aerial battle against the Separatist invasion forces."
				+ " Upon Anakin Skywalker�s revelation that Palpatine was in fact Darth Sidious, Fisto, along with Mace Windu, Agen Kolar, and Saesee Tiin, attempted to arrest the Supreme Chancellor,"
				+ " but failed. In that final confrontation with the scheming Sith Lord, Fisto perished at the hands of Darth Sidious.");
		article.setImageDescriptor(ArticleImages.KIT_FISTO.getImageDescriptor());
		article.setUnit(Unit.PIECE);
		return article;
	}

	/**
	 * @return
	 */
	public Article createNuteGunrayArticle() {
		Article article = new Article();
		article.generateAndSetId(counter);
		article.setArticleNumber("Nute Gunray");
		article.setDescription("Nute Gunray was a Neimoidian male who served as a stint Minister of the Trade Federation until he rose in power after the Stark Hyperspace War in 44 BBY,"
				+ " eventually becoming Viceroy of the Trade Federation. In 32 BBY, Gunray participated in the occupation of Naboo with the Federation Army to settle a"
				+ " trade dispute that began in the Galactic Senate. With the help of Darth Sidious, Gunray invaded the planet to end the trade dispute and secured all facilities"
				+ " in the capital of Theed. He attempted to force the incumbent Queen of Naboo Padmé Amidala to sign a treaty to make his invasion legal, however she refused and"
				+ " the Siege of Naboo continued. The Trade Federation soon surrendered to Naboo forces following the Battle of Naboo. Gunray was taken to the Galactic Republic capital"
				+ " on Coruscant to be charged with acts of war. During the Separatist Crisis that following the Invasion of Naboo, Gunray confronted former Jedi Master, Count Dooku,"
				+ " who began a separatist movement that opposed the Republic. Dooku, who secretly was Sidious's Sith apprentice under the name of Darth Tyranus, helped him through his"
				+ " four trials in the Supreme Court, and soon after the trials, Gunray retained his position as Viceroy. The Trade Federation, under Gunray's command, joined Dooku's"
				+ " separatist movement and Gunray was named Head of the newly formed Separatist Council and so formed the Separatist Army. Gunray, along with Dooku and his master, Sidious,"
				+ " formed the Confederacy of Independent Systems which opposed the Galactic Republic in a three-year war known as the Clone Wars that followed after two years of the Separatist Crisis."
				+ " During the Clone Wars, Gunray approached Onaconda Farr, the senator of Rodia, with an offer he couldn't refuse: join the Separatists, and receive the aid and protection for"
				+ " Rodia that the Republic had failed to provide. However, Gunray's motivations for the generous offer were far from friendly; in return, he demanded that Farr lure Senator"
				+ " Amidala to the planet and hand her over to him. Gunray's plan ultimately failed and he was captured by the Republic and given to Jedi Master Luminara Unduli and Jedi Padawan Ahsoka Tano."
				+ " Dooku sent his Dark Jedi apprentice, Asajj Ventress, to rescue the Viceroy from the Republic forces. Stationed inside the Republic Star Destroyer, Senate Commando Faro"
				+ " Argyus released the Viceroy and met up with Ventress in Argyus's cruiser. Argyus was betrayed and murdered by Ventress, however, in a fit of rage over his arrogance."
				+ " In 19 BBY, the Separatist Council was moved to the volcanic planet of Mustafar from the sink hole planet of Utapau, after Separatist General Grievous became leader of"
				+ " the CIS after the death of Dooku. Following the death of Grievous at the hands of Obi-Wan Kenobi Nute Gunray became the new leader of the separatists."
				+ " Gunray along with the other Separatist leaders were betrayed by Darth Sidious after he sent his new apprentice, Darth Vader, to Mustafar to end the war."
				+ " Gunray was the last council member to be killed by Vader's lightsaber, which had ultimately brought an end to the Clone Wars and fragmented the CIS into various Separatist holdouts."
				);
		article.setImageDescriptor(ArticleImages.NUTE_GUNRAY.getImageDescriptor());
		article.setUnit(Unit.PIECE);
		return article;
	}

	/**
	 * @return
	 */
	public Article createPadmeAmidalaArticle() {
		Article article = new Article();
		article.generateAndSetId(counter);
		article.setArticleNumber("Padmé Amidala");
		article.setDescription("Princess of Theed, Queen of Naboo, Senator of the Chommell Sector and Naboo");
		article.setImageDescriptor(ArticleImages.PADME_AMIDALA.getImageDescriptor());
		article.setUnit(Unit.PIECE);
		return article;
	}

	/**
	 * @return
	 */
	public Article createPloKoonJediArticle() {
		Article article = new Article();
		article.generateAndSetId(counter);
		article.setArticleNumber("Plo Koon");
		article.setDescription("Plo Koon was a Kel Dor male from the planet Dorin who became a Jedi Master and a lifetime member of the Jedi High Council,"
				+ " holding the position from after the Stark Hyperspace War to the end of the Galactic Republic in 19 BBY. During the Clone Wars,"
				+ " Koon served as a Jedi General in the Grand Army of the Republic, leading soldiers in campaigns, fighting on Geonosis and at Kaliida"
				+ " Shoals amongst others. Koon was also an accomplished starfighter pilot. Koon had an uncle and a niece, Sha Koon, who was also a member"
				+ " of the Jedi Order, and served as a personal communicator between the Jedi strike force and the rest of the Republic during the"
				+ " Stark Hyperspace War. He was a close friend of fellow Masters Qui-Gon Jinn (whom he fought alongside during the Stark Hyperspace War),"
				+ " Micah Giiett, Ki-Adi-Mundi and noted fighter pilot Saesee Tiin. Plo Koon was shot down over Cato Neimoidia by his clone troopers,"
				+ " killing him in compliance with Order 66.");
		article.setImageDescriptor(ArticleImages.PLO_KOON_JEDI.getImageDescriptor());
		article.setUnit(Unit.PIECE);
		return article;
	}

	/**
	 * @return
	 */
	public Article createR2D2Article() {
		Article article = new Article();
		article.generateAndSetId(counter);
		article.setArticleNumber("R2D2");
		article.setDescription("Astromech droid");
		article.setImageDescriptor(ArticleImages.R2D2.getImageDescriptor());
		article.setUnit(Unit.PIECE);
		return article;
	}

	/**
	 * @return
	 */
	public Article createSebulbaArticle() {
		Article article = new Article();
		article.generateAndSetId(counter);
		article.setArticleNumber("Sebula");
		article.setDescription("Sebulba was a male Dug hailing from the Mid Rim planet Malastare. He was a former slave who had bought his freedom with his incredible"
				+ " skills at piloting a high-speed Podracer, and the well-known arch-rival of a young Human Podracer named Anakin Skywalker."
				+ " Before long, Sebulba became the star racer of the Galactic Podracing Circuit, based not only on his formidable racing"
				+ " skills but also his penchant for violence. Many fellow Podracers were killed or injured by Sebulba's reckless steering"
				+ " or out-and-out cheating, but Sebulba was far too popular to be blamed or penalized.");
		article.setImageDescriptor(ArticleImages.SEBULBA.getImageDescriptor());
		article.setUnit(Unit.PIECE);
		return article;
	}

	/**
	 * @return
	 */
	public Article createSuperBattleDroidArticle() {
		Article article = new Article();
		article.generateAndSetId(counter);
		article.setArticleNumber("B2 super battle droid");
		article.setDescription("The B2 super battle droid (SBD) was an upgraded version of the B1 battle droid, with superior armament and armor."
				+ " They were manufactured after the Invasion of Naboo proved need for stronger droids. B2s were used by the Trade Federation,"
				+ " the Techno Union, and later, on a much larger scale, the Confederacy of Independent Systems. Some units were later reactivated on"
				+ " Mustafar by Gizor Dellso and other members of the Separatist holdouts, and the Galactic Alliance also used some B2 units during the"
				+ " Yuuzhan Vong War.");
		article.setImageDescriptor(ArticleImages.SUPER_BATTLE_DROID.getImageDescriptor());
		article.setUnit(Unit.PIECE);
		return article;
	}

	/**
	 * @return
	 */
	public Article createTuskenRidersArticle() {
		Article article = new Article();
		article.generateAndSetId(counter);
		article.setArticleNumber("Tusken Raiders");
		article.setDescription("Less formally referred to as Sand People or simply as Tuskens, were a culture of nomadic, primitive sentients indigenous to Tatooine,"
				+ " where they were often hostile to local settlers. The term Sand People was given to them due to their existence in the desert, and was in use from"
				+ " at least around 4000 BBY; but the more formal name of Tusken Raiders was acquired much later, due to a period of concerted attacks on the settlement"
				+ " at Fort Tusken in 98-95 BBY. Although this name is often used as a common term for the race, it actually refers only to the participants of the attack"
				+ " on the settlement.\\n\\n Specialists studying the past of the Tusken Raiders also used the term Ghorfa to denote an earlier sedentary phase of their culture,"
				+ " and lastly Kumumgah, for the earliest stratum of sentient civilization on the planet, believed by some to represent a common ancestry shared by the Ghorfa"
				+ " and the Jawas. In the culture of the Tuskens, to expose any part of the flesh was forbidden and seen as a disgrace.");
		article.setImageDescriptor(ArticleImages.TUSKEN_RIDERS.getImageDescriptor());
		article.setUnit(Unit.PIECE);
		return article;
	}

	/**
	 * @return
	 */
	public Article createWattoArticle() {
		Article article = new Article();
		article.generateAndSetId(counter);
		article.setArticleNumber("Watto");
		article.setDescription("Was a male Toydarian junk dealer/Human trafficker, who owned a shop in Mos Espa, Tatooine. In his youth, he served as a soldier in"
				+ " the Ossiki Confederacy Army on his homeworld of Toydaria, but later left after sustaining permanently damaging injuries. Making his"
				+ " way to Tatooine, he fell in with the Jawa natives of the planet, and learned how to trade from them. Once he felt he had learned all"
				+ " he could, he abandoned the Jawas, and set up Watto's Shop in Mos Espa. He became one of Tatooine's many slave owners, and made a lucrative"
				+ " business for himself. Eventually, he came into possession of a young slave, Anakin Skywalker, who proved to be a gifted mechanic, and an"
				+ " invaluable asset to the running of the store. Soon enough, Watto discovered that the boy had an affinity for podracing.Watto would often"
				+ " bet on the podraces, and was an avid fan, so he had Anakin race for him in several tournaments. Although the boy never won, he was clearly gifted."
				+ " During one particular race, Anakin destroyed Watto's own podracer, which incurred the Toydarian's wrath. Shortly thereafter, a stranger to Mos Espa,"
				+ " Qui-Gon Jinn, claimed that he had his own podracer, and that he wanted Anakin to fly it in the Boonta Eve Classic. Watto conceded to the deal,"
				+ " but decided to bet against Anakin, instead investing in the Dug Sebulba. Jinn took the bet, wagering his ship against Anakin's freedom."
				+ " Ultimately, Anakin won the race, and Watto was left with very little. His business declined, and he eventually had to sell Anakin's mother,"
				+ " Shmi Skywalker. Watto stayed in business until the Galactic Civil War, when he handed over ownership of his shop to his assistant, W. Wald.");
		article.setUnit(Unit.PIECE);
		article.setImageDescriptor(ArticleImages.WATTO.getImageDescriptor());
		return article;
	}

}
