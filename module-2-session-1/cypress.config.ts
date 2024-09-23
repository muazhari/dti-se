import {defineConfig} from 'cypress'
import registerCodeCoverageTasks from '@cypress/code-coverage/task'

export default defineConfig({
    env: {
        baseUrl: 'http://localhost:3000',
        codeCoverage: {
            url: '/api/__coverage__',
        },
    },
    e2e: {
        specPattern: "./test/**/*.spec.ts",
        supportFile: "./test/e2e.ts",
        setupNodeEvents(on, config) {
            registerCodeCoverageTasks(on, config)
            return config
        },
    },
})
