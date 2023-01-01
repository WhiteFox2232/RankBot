# RankBot
RankBot est un bot Discord créé en Java avec la Library JDA. Celui-ci vous permet d'établir un classement d'activité sur votre serveur, en vocal ou en écrit. Celui-ci supporte uniquement MongoDB pour le stockage.

### Sommaire
 - Get Started
 - Commandes
 - Fonctionnement
 
 ## Get Started
Voici les différentes étapes à suivre afin de mettre en place le Bot sur votre serveur
1. Téléchargez le repository Github, puis compilez le projet afin d'obtenir un ficher .jar.
2. Rendez-vous sur le [panel développeur de Discord](https://discord.com/developers/applications) afin de créer une nouvelle applications 
et un nouveau Bot. Lorsque vous créerez le lien d'invitations OAuth2, 
assurez-vous d'ajouter les SCOPES `applications.commands` et `bot`, en permission brute 
vous pouvez mettre la permission `Administrator`. Le bot n'a besoin que de voir les salons vocaux
et de lire les messages. La permission administrateur vous empêche de devoir configurer chaque permission dans chaque salon.
3. Créez une nouvelle base de donnée MongoDB. Vous pouvez utiliser le service [Atlas](https://www.mongodb.com/atlas) afin d'avoir une base de données
gratuite, mais les performances seront limitées. Sachez toutefois que peu importe le service que vous prendrez, votre base de données devra avoir
une **database** appelée "`RankBot`" ainsi qu'une **collection** appelée "`DB`", vous pouvez évidemment changer ces noms en modifiant le code du bot.
4. Lancez le .jar précédement créé en mettant bien en argument le token du bot précédemment obtenu.
Exemple : `java -jar DiscordRankBot.jar VOTRE_TOKEN`.
5. Faites directement la commande `/config bdd LIEN` en remplaçant "LIEN" par le lien de votre base de données MongoDB.
6. Le bot ne fonctionnant que sur un seul serveur, faites la commande `/config server ID` en remplaçant "ID" par l'ID du serveur en question.

## Commandes
Voici une liste des commandes actuellement disponibles sur le bot, classées en commandes et sous-commandes.
- `/config`» Commandes de configuration de base du bot, il est fortement déconseillé de laisser la permission à tous d'utiliser ces commandes.
  - `/config bdd` » Configurez la base de donnée à lier au bot.
  - `/config server` » Configurez le serveur sur lequel vous souhaitez que le bot récolte l'activité
- `/rank` » Commandes de base du bot afin d'obtenir des informations sur l'xp que possèdent les membre de votre serveur.
  - `/rank user` » Vous permet d'obtenir l'xp ainsi que le niveau d'un utilisateur spécifique.
  - `/rank top` » Vous permet d'obtenir le classement des 10 premiers ayant le plus haut level (si égalité xp).
- `/boost` » Définissez ou obtenez le multiplicateur (boost) général d'xp du serveur. Il est également déconseillé de laisser la permission à tous 
d'utiliser ces commandes.
  - `/boost get` » Obtenez le boost actuellement défini (par défaut x1)
  - `/boost set` » Définissez le boost d'xp du serveur.
  
## Fonctionnement
#### En vocal :
Chaque minute, l'intégralité des membres actuellement en vocal gagneront un nombre d'xp aléatoire compris entre **5** et **12**.
Lorsqu'un membre se déconnecte d'un salon vocal, son xp est envoyée à la base de données. Il doit être complètement déconnecté, si il change de salon son xp ne sera pas envoyée. Le bot ne donnera pas d'xp si le membre en question est seul dans le salon vocal ou qu'il est mute.
#### À l'écrit :
Chaque message envoyé par un membre de votre serveur lui fera gagner un nombre d'xp aléatoire compris entre **10** et **24**.
Évidemment, afin d'éviter le spam, l'xp sera limitée à 1 message par minute.
