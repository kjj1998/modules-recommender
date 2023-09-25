function AboutPage() {
  return (
    <main className='mx-auto max-w-4xl py-1 min-h-screen mt-10'>
      <div className="mt-16 text-lg">
        <h1 className="text-3xl text-blue-600 font-bold">About</h1>
        <p className="mt-5">
          Hi everyone,
        </p>
        <p className="mt-5">
          This is the developer of Modules2Students, Jun Jie.
        </p>
        <p className="mt-5">
          Modules2Students is my Final Year Project to fulfill my Bachelors of Engineering in Computer Science at the <a href="https://www.ntu.edu.sg/" className="text-purple-700 hover:underline font-bold">Nanyang Technological University (NTU)</a>.
          In the course of completing this project, I was supervised by Assistant Professor <a href="https://personal.ntu.edu.sg/c.long/" className="text-purple-700 hover:underline font-bold">Long Cheng</a> from the School of Computer Science and Engineering for whom I am thankful for his guidance and support throughout the project duration.
        </p>
        <p className="mt-5">
          Modules2Students is a modules recommender system for undergraduate students at NTU. The recommender system is able to recommend modules based on the modules that student has taken (content-based filtering) and based on modules that other students with similar profiles to the current student has taken (collaborative filtering).
        </p>
        <p className="mt-5">
          The source code and instructions to replicate this project can be found at my <a href="https://github.com/kjj1998/modules2Students" className="text-purple-700 hover:underline font-bold">Github repository</a>.
        </p>
        <p className="mt-5">
          Please direct any relevant queries to my <a className="hover:underline font-bold" href="mailto:koh.jun.jie@outlook.com">email</a>.
        </p>
      </div>
    </main>
  )
}
  
export default AboutPage