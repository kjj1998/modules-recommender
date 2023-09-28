import Link from 'next/link';
import Head from 'next/head';

function HomePage() {
  return (
    <div>
      <Head>
        <title>modules2students</title>
        <meta 
          name='modules2students'
          content='A recommender system for Nanyang Technological University (NTU) modules.'
        />
      </Head>
      <div>
        <h1 className="mt-56 mb-12 text-3xl text-center dark:text-white">
          Hello and Welcome 👋&nbsp;
          <span className="whitespace-nowrap">
            to <span className="font-bold">modules2students</span> a recommendation system for undergraduate modules at NTU.
          </span>
        </h1>
        <p className='mt-12 mb-12 text-2xl text-center dark:text-white'>
          Click on the Modules link above to view all available modules or sign in and view your personalized recommendations!
        </p>
        <p className='mt-12 mb-12 text-2xl text-center dark:text-white'>
          Do note that only registered users can view personalized recommendations
        </p>
      </div>
    </div>
  );
}

export default HomePage;