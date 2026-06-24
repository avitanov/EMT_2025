import { defineConfig, loadEnv } from 'vite'
import react from '@vitejs/plugin-react'
import { fileURLToPath } from 'node:url'

const rootEnvDir = fileURLToPath(new URL('..', import.meta.url))

export default defineConfig(({ mode, command }) => {
  const env = loadEnv(mode, rootEnvDir, '')
  const requiredEnv = command === 'serve'
    ? ['FRONTEND_PORT', 'VITE_API_BASE_URL', 'VITE_API_PROXY_TARGET']
    : ['VITE_API_BASE_URL']

  for (const key of requiredEnv) {
    if (!env[key]) {
      throw new Error(`${key} must be configured in the root .env file`)
    }
  }

  const config = {
    envDir: rootEnvDir,
    plugins: [react()],
  }

  if (command === 'serve') {
    config.server = {
      port: Number(env.FRONTEND_PORT),
      proxy: {
        [env.VITE_API_BASE_URL]: {
          target: env.VITE_API_PROXY_TARGET,
          changeOrigin: true,
        },
      },
    }
  }

  return config
})
