import type { Config } from 'tailwindcss'

const config: Config = {
  content: [
    './pages/**/*.{js,ts,jsx,tsx,mdx}',
    './components/**/*.{js,ts,jsx,tsx,mdx}',
    './app/**/*.{js,ts,jsx,tsx,mdx}',
  ],
  theme: {
    extend: {
      backgroundImage: {
        'gradient-radial': 'radial-gradient(var(--tw-gradient-stops))',
        'gradient-conic':
          'conic-gradient(from 180deg at 50% 50%, var(--tw-gradient-stops))',
      },
      margin: {
        '112': '28rem',
        '128': '32rem',
        '160': '40rem'
      },
      colors: {
        'just-blue': '#0000ff',
        'neon-blue': '#1f51ff'
      },
    },
  },
  plugins: [],
}
export default config
