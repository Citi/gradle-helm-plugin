<div align="center">
  <a href="https://github.com/citi">
    <img src="https://github.com/citi.png" alt="Citi" width="80" height="80">
  </a>

<h3 align="center">Citi/gradle-helm-plugin</h3>

  <p align="center">
    A suite of Gradle Plugins for <br />building, publishing and managing <a href="https://www.helm.sh">Helm</a> Charts
    <br />
  </p>

  <p align="center">
    <a href="https://citi.github.io/gradle-helm-plugin/"><img src="https://img.shields.io/badge/read%20our%20documentation-0f1632"></a>
    <a href="https://plugins.gradle.org/plugin/com.citi.helm/2.2.0"><img src="https://img.shields.io/gradle-plugin-portal/v/com.citi.helm?versionPrefix=2.2.0&colorA=0f1632&colorB=255be3" /></a>
    <a href="./LICENSE"><img src="https://img.shields.io/github/license/citi/gradle-helm-plugin?label=license&colorA=0f1632&colorB=255be3" /></a>
    <a href="https://img.shields.io/ossf-scorecard/github.com/Citi/gradle-helm-plugin?label=openssf+scorecard&style=flat"><img src="https://img.shields.io/ossf-scorecard/github.com/Citi/gradle-helm-plugin?label=openssf+scorecard&style=flat" /></a>
  </p>
</div>

<br />

## Features

- Gradle task types for common Helm CLI commands

- Build, package and publish Helm Charts using a declarative Gradle DSL

- Resolve placeholders like ${chartVersion} from chart source files before packaging

- Resolve dependencies between charts using Gradle artifact dependencies

- Install, upgrade and uninstall releases to/from a Kubernetes cluster

## Quick Start

Add `io.github.gradle-cloud.helm` to your Gradle project:

```gradle
plugins {
    id 'io.github.gradle-cloud.helm' version '2.2.0'
}
```

```gradle
plugins {
    id("io.github.gradle-cloud.helm") version "2.2.0"
}
```

```gradle
📂 (project root)
    📂 src
        📂 main
            📂 helm
                📂 templates
                    📄 ...
                📄 Chart.yaml
                📄 values.yaml
```

## Migration from older versions

This repository is a fork of [Citi/gradle-helm-plugin](https://github.com/Citi/gradle-helm-plugin), which is a
of [unbroken-dome/gradle-helm-plugin](https://github.com/unbroken-dome/gradle-helm-plugin).

The version [v2.2.0](https://github.com/Gradle-Cloud/gradle-helm-plugin/releases/tag/v2.2.0) has exactly the same code
with version [2.2.0](https://github.com/Citi/gradle-helm-plugin/releases/tag/2.2.0)
of [Citi/gradle-helm-plugin](https://github.com/Citi/gradle-helm-plugin). Therefore, first please use that version. All
Java/Kotlin packages are the same, so the plugin should be fully compatible.

Version `3.0.0` (under construction) is not backward compatible with older plugin versions, because:

* It has new Java/Kotlin packages that match this project.
* It is compatible with Gradle 9 (and not with Gradle 7), and all old APIs have been removed.
* The minimal Java version is Java 17 (not 1.8).

## Requirements

- Gradle 7 or higher

- JDK 1.8 or higher (for running Gradle)

- Helm command-line client 3.+

## Contributing

Your contributions are at the core of making this a true open source project. Any contributions you make are **greatly
appreciated**.

We welcome you to:

- Fix typos or touch up documentation
- Share your opinions on [existing issues](https://github.com/citi/gradle-helm-plugin/issues)
- Help expand and improve our library by [opening a new issue](https://github.com/citi/gradle-helm-plugin/issues/new)

Please review our [community contribution guidelines](https://github.com/Citi/.github/blob/main/CONTRIBUTING.md)
and [functional contribution guidelines](./CONTRIBUTING.md) to get started 👍

## Code of Conduct

We are committed to making open source an enjoyable and respectful experience for our community. See [
`CODE_OF_CONDUCT`](https://github.com/Citi/.github/blob/main/CODE_OF_CONDUCT.md) for more information.

## License

This project is distributed under the [MIT License](https://opensource.org/license/mit). See [`LICENSE`](./LICENSE) for
more information.

## Contact

If you have a query or require support with this
project, [raise an issue](https://github.com/Citi/gradle-helm-plugin/issues). Otherwise, reach out to
opensource@citi.com.
