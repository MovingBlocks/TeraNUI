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

In Terasology, you can test easily locally until you're ready for a snapshot or release. To do so, you'll need to re-embed TeraNUI into Terasology as source which has priority over any binary version. You can get the TeraNUI library as source into your local Terasology workspace as follows:

```sh
groovyw lib get TeraNUI
```

This will place the TeraNUI source code into `/libs/TeraNUI`. Please note, that you may need to reload/refresh your gradle workspace in Intellij IDEA.

## 🤖 Maintenance

The main development of TeraNUI happens on the `master` branch, a strong main branch that always has the latest version
of the code. 
With TeraNUI v3 we can support both versions of [gestalt] in a single release (v5 and v7, respectively).

We keep _release branches_ for TeraNUI's major versions.
Note that new features will only be implemented for the latest version on `master`.
The following table shows release branches and known projects using them.
 

| Branch         | Version | gestalt | Projects |
| -------------- |:-------:|:-------:| -------- |
| `master`       | v3.x    | \*      | Terasology 4.4+ |
| `release/v2.x` | v2.x    | v7      | [DestinationSol] |
| `release/v1.x` | v1.x    | v5      | [Terasology] prior to 4.4.0 |

We may port back changes made to the main line of the code to release branches by _cherry picking_ them to the respective branches.

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

### Release Process

As releases are automatically triggered from `master` and `release/v{{major-version}}.x` the required steps to make a 
non-snapshot release for any version is as follows:

1. **Decide on release version** ▪ Which branch to publish, under which version?

    _The version number MUST be a higher SemVer than the current version of the branch to release.
     The version bump SHOULD follow SemVer specifications, e.g., increase the major version for breaking changes, or do
     a patch release for bug fixes._
 
1. **Make the release commit** ▪ Trigger a release via [Jenkins]

    _Update the version in [build.gradle](./build.gradle) and remove the `-SNAPSHOT` suffix. Commit the change with the
     following message:_

    > `release: version {{version}}`

    _Until we have automatic tagging or a tag-based release process it is recommended to manually
     [create and push an annotated tag][git-tag] for the respective version on `master`. For a library release v1.2.3
     the tag process is:_
    
    ```sh
    git tag -a v1.2.3 -m "Release version 1.2.3"
    git push --tags
    ```
    
1. **Prepare for next release** ▪ Bump to next snapshot version

    _Finally, we have to increase the version number to be able to get pre-release `-SNAPSHOT` builds for subsequent 
     commits. Therefore, the version number MUST be a higher SemVer than the version just released. This will typically
     be a minor version bump. To do this, just update the version in [build.gradle](./build.gradle) and commit the 
     change with the following message:_
    
    > `chore: prepare next snapshot for {{version}}`

## License

This library is licensed under the [Apache 2.0 License](http://www.apache.org/licenses/LICENSE-2.0.html).

<!-- References -->
[artifactory]: http://artifactory.terasology.org/
[artifactory-nui-snapshot]: http://artifactory.terasology.org/artifactory/webapp/#/artifacts/browse/simple/General/libs-snapshot-local/org/terasology/nui
[artifactory-nui-release]: http://artifactory.terasology.org/artifactory/webapp/#/artifacts/browse/simple/General/libs-release-local/org/terasology/nui
[destinationsol]: https://github.com/MovingBlocks/DestinationSol
[gestalt]: https://github.com/MovingBlocks/gestalt
[git-tag]: https://www.atlassian.com/git/tutorials/inspecting-a-repository/git-tag
[guide]: https://terasology.org/TeraNUI
[javadoc]: http://jenkins.terasology.io/teraorg/job/Libraries/job/TeraNUI/job/master/javadoc/overview-summary.html
[jenkins]: http://jenkins.terasology.io/teraorg/job/Libraries/job/TeraNUI/
[terasology]: https://github.com/MovingBlocks/Terasology
[tutorial]: https://github.com/Terasology/TutorialNUI/wiki
