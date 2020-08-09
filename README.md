# TeraNUI

**TeraNUI** (Terasology's New UI Framework) is a canvas based UI framework.

Its major features are:

- Canvas providing primitive draw operations for UI, including "drawing" interaction regions.
- Skins providing display information, handled through the canvas operations.
- UIWidget system for encapsulating drawing logic into objects
- Skin asset providing the ability to define skins
- UI asset providing the ability to define widget layouts
- Data binding support

Learn more about TeraNUI and how to use it here:

👉 [JavaDoc] ▪ [Guide] ▪ [Tutorial] 👈

## 🤓 Development

We welcome contributions to this UI library, be it bug fixes or new features. Feel free to fork this project and open a 
PR with your changes. You can start right away by using the Gitpod online workspace:

[![Open in Gitpod](https://gitpod.io/button/open-in-gitpod.svg)](https://gitpod.io/#https://github.com/MovingBlocks/TeraNUI)

## 🤖 Maintenance

The main development of TeraNUI happens on the `master` branch, a strong main branch that always has the latest version
of the code. However, we maintain two versions of TeraNUI for [gestalt] v5 and v7, respectively. As the two code lines are nearly identical most changes to either version should be [ported back 🔗](https://docs.microsoft.com/en-us/azure/devops/repos/git/git-branching-guidance?view=azure-devops#port-changes-back-to-the-main-branch) to the other. 
 

| Branch         | Version | gestalt | Projects |
| -------------- |:-------:|:-------:| -------- |
| `master`       | v2.x    | v7      | [DestinationSol] |
| `release/v1.x` | v1.x    | v5      | [Terasology]

**How to back-port changes?** 
That's a question I'm always asking myself when submitting something to gestalt. I'd like to have a least a rough 
guideline how to backport a change to TeraNUI v1 so that every contributor with write permissions on this repo can do this.

- NUI is tied to a specific version of [gestalt]
- **v1.x** works with gestalt v5 (compatible with [Terasology])
- **v2.x** works with gestalt v7 (compatible with [DestinationSol])
- active development for v2 happens on the `master` branch
- maintenance of v1 happens the release branch `release/v1`
- changes and bug-fixes should be ported back to v1
    - create a new feature branch off the release branch to port the changes
    - cherry-pick the changes from the main branch to the feature branch
    - merge the feature branch back into the release branch


## 🚀 Release Management

A _release_ denotes that an artifact for the associated commit is available for consumption. Our
[Jenkins CI/CD pipeline][jenkins] automatically builds and publishes releases for the main `master` branch and release
branches prefixed with `release/v{{major-version}}.x`. The artifact is published to our [Artifactory] under the version specified
in [`build.gradle`](./build.gradle).

> ⚠ **Note:** Whether an artifact should be published as release or snapshot is determined by whether or not there is a
> `-SNAPSHOT` in the version. Publishing will fail in case publishing the same non-snapshot version is attempted
> again.

The exact build steps for this library are defined in the [Jenkinsfile](./Jenkinsfile).

🗄 [**Snapshots**][artifactory-nui-snapshot] ▪ [**Releases**][artifactory-nui-release]

> 🚧 TODO: how to consume TeraNUI from the Terasolgoy Artifactory (e.g., gradle dependency snippet)

## License

This library is licensed under the [Apache 2.0 License](http://www.apache.org/licenses/LICENSE-2.0.html).

<!-- References -->
[gestalt]: https://github.com/MovingBlocks/gestalt
[terasology]: https://github.com/MovingBlocks/Terasology
[destinationsol]: https://github.com/MovingBlocks/DestinationSol
[guide]: https://terasology.org/TeraNUI
[javadoc]: http://jenkins.terasology.io/teraorg/job/Libraries/job/TeraNUI/job/master/javadoc/overview-summary.html
[tutorial]: https://github.com/Terasology/TutorialNUI/wiki
[jenkins]: http://jenkins.terasology.io/teraorg/job/Libraries/job/TeraNUI/
[artifactory]: http://artifactory.terasology.org/
[artifactory-nui-snapshot]: http://artifactory.terasology.org/artifactory/webapp/#/artifacts/browse/simple/General/libs-snapshot-local/org/terasology/nui
[artifactory-nui-release]: http://artifactory.terasology.org/artifactory/webapp/#/artifacts/browse/simple/General/libs-release-local/org/terasology/nui
