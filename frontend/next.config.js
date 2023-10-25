/** @type {import('next').NextConfig} */
const nextConfig = {
  reactStrictMode: true,
  output: 'standalone',
  // env: {
  //   HOST: process.env.HOST || 'localhost', //read the value from env id empty use default value.
  // },
}

module.exports = nextConfig
