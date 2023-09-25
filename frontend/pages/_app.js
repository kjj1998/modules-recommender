import Layout from '@/components/layout/Layout'
import Head from 'next/head'
import '@/styles/globals.css'
import { NotificationContextProvider } from '../store/notification-context'
import { SessionProvider } from "next-auth/react"

export default function App({ Component, pageProps: { session, ...pageProps } }) {
  return (
    <SessionProvider session={session}>
      <NotificationContextProvider>
        <Layout>
          <Head>
            <meta name="viewport" content="initial-scale=1.0, width=device-width" />
          </Head>
          <Component {...pageProps} />
        </Layout>
      </NotificationContextProvider>
    </SessionProvider>
  ) 
}
