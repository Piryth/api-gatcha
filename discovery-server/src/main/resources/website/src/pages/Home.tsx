export const Home = () => {
  return (
    <div className='container mx-auto p-6'>
      <div className='text-center my-8'>
        <h1 className='text-4xl font-bold text-primary mb-4'>Projet API Gatcha</h1>
        <p className='text-lg text-gray-700 dark:text-gray-300'>
          Un système complet d'API pour un jeu de type Gatcha, prêt à être déployé.
        </p>
      </div>

      <div className='grid grid-cols-1 md:grid-cols-2 gap-8'>
        {/* Présentation du projet */}
        <div className='bg-background p-6 rounded-xl shadow-lg'>
          <h2 className='text-2xl font-semibold text-primary mb-4'>Présentation du projet</h2>
          <p className='text-gray-700 dark:text-gray-300'>
            Nous avons développé un système d'API complet pour un jeu de type Gatcha. Ce projet a été conçu pour être simple à déployer et
            entièrement opérationnel dès sa récupération.
          </p>
        </div>

        {/* Stack & Technologies */}
        <div className='bg-background p-6 rounded-xl shadow-lg'>
          <h2 className='text-2xl font-semibold text-primary mb-4'>Technologies utilisées</h2>
          <ul className='list-disc list-inside text-gray-700 dark:text-gray-300'>
            <li>
              📦 <strong>Docker</strong> pour un déploiement facile et rapide
            </li>
            <li>
              🗄️ <strong>MongoDB</strong> comme base de données
            </li>
            <li>
              🚀 <strong>SpringBoot</strong> pour la gestion des API
            </li>
            <li>
              📝 <strong>Tests unitaires</strong> couvrant chaque API
            </li>
            <li>
              📂 <strong>Git</strong> utilisé pour la gestion de projet en équipe
            </li>
          </ul>
        </div>

        {/* Déploiement & Setup */}
        <div className='bg-background p-6 rounded-xl shadow-lg'>
          <h2 className='text-2xl font-semibold text-primary mb-4'>Déploiement et configuration</h2>
          <p className='text-gray-700 dark:text-gray-300'>
            Le projet tourne **intégralement** sous **Docker**. Un fichier{' '}
            <code className='bg-gray-200 px-1 rounded'>docker-compose.yml</code> est fourni pour permettre un déploiement instantané sur
            n'importe quelle machine.
          </p>
        </div>

        {/* Objectifs Bonus */}
        <div className='bg-background p-6 rounded-xl shadow-lg'>
          <h2 className='text-2xl font-semibold text-primary mb-4'>Tests & collaboration</h2>
          <p className='text-gray-700 dark:text-gray-300'>
            🔥 **Qualité garantie :** Toutes les API sont couvertes par des **tests unitaires rigoureux**. 💡 **Travail d'équipe :** Ce
            projet a été développé en groupe de 4 personnes avec Git pour assurer une bonne gestion du code.
          </p>
        </div>
      </div>

      <div className='text-center mt-12'>
        <p className='text-lg text-gray-700 dark:text-gray-300'>Le projet est prêt, il ne reste plus qu'à jouer ! 🎮🚀</p>
      </div>
    </div>
  );
};
