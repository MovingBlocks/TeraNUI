# TeraNUI: Documentation

This is the technical documentation for **TeraNUI**. It is intended to help developers working with Terasology's UI framework to build user and theme in-game user interfaces.

The documentation is automatically deployed on each commit to `master` and can be found online here: 

> https://movingblocks.github.io/TeraNUI/

## Building and Viewing

The documentation uses Rust's [*mdbook*](https://github.com/rust-lang/mdBook) crate. Please check out the 
[*mdbook* user guide 🔗](https://rust-lang.github.io/mdBook/) for more details. There are different ways to edit and 
build the page:

- **☁ online** - This repository is enabled for use with [gitpod.io](https://www.gitpod.io). You can launch an instance of VS Code online and start editing the page.<br>
  [![Open in Gitpod](https://gitpod.io/button/open-in-gitpod.svg)](https://gitpod.io/#https://github.com/MovingBlocks/TeraNUI)
- **🐳 locally (with devcontainer in VSCode with Docker)** - This repository is enabled for usage with VS Code *development containers* for local development via the [Remote - Containers](https://marketplace.visualstudio.com/items?itemName=ms-vscode-remote.remote-containers) plugin. The development container requires [Docker](https://www.docker.com/). There is no need to install and setup Rust separately.
  > ℹ We use the same Docker image (`gitpod/mdbook`, 2.5 GB ⚠) for the devcontainer and gitpod setup. 
- **🖥 locally (with Rust)** - Please ensure that you have a working copy of [Rust installed](https://www.rust-lang.org/tools/install). In addition, install _mdbook_ ([detailed install steps 🔗](https://rust-lang.github.io/mdBook/cli/index.html)) with:
    ```sh
    cargo install mdbook
    ```

To generate the static website and serve it locally run the following in this directory:

```sh
cd docs
mdbook serve
```

You can then view the documentation in a web-browser at: http://localhost:3000
