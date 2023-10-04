import NextAuth from "next-auth/next";
import CredentialsProvider from "next-auth/providers/credentials";

export default NextAuth({
  session: {
    jwt: true
  },
  secret: process.env.NEXTAUTH_SECRET,
  providers: [
    CredentialsProvider({
      async authorize(credentials) {

        const response = await fetch(`http://localhost:8081/api/v1/auth/authenticate`, {
          method: 'POST',
          headers: { 'Content-Type': 'application/json' },
          body: JSON.stringify(credentials)
        })

        const result = await response.json()
        const data = result.data

        return data
      }
    })
  ],
  callbacks: {
    async jwt({ token, user, userid }) {

      return { ...token, ...user, ...userid }
    },
    async session({ session, token}) {
      // Send properties to the client, like an access_token and user id from a provider.
      session.user = token
      
      return session
    }
  }
})