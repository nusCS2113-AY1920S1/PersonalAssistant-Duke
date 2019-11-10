# Contributing to AlgoSenpai Adventures

Contributions to Algosenpai Adventures include code, documentation, answering user questions,
and running the project's infrastructure.

This guide explains the process for contributing to this project's core
`AY1920S1-CS2113T-T09-3/main` GitHub Repository and describes what to expect at each step.

## <a name="commit"></a>Commit Message Guidelines

We have very precise rules over how our git commit messages can be formatted. This leads to **more
readable messages** that are easy to follow when looking through the **project history**.

### Commit Message Format

Each commit message consists of a **header**, a **body** and a **footer**. The header has a special
format that includes a **type**, a **scope** and a **subject**:

```
<type>(<scope>): <subject>
<BLANK LINE>
<body>
<BLANK LINE>
<footer>
```

The **header** is mandatory and the **scope** of the header is optional.

Any line of the commit message cannot be longer 100 characters! This allows the message to be easier
to read on GitHub as well as in various git tools.

The footer should contain a [closing reference to an issue](https://help.github.com/articles/closing-issues-via-commit-messages/) if any.

Samples: (even more [samples](https://github.com/angular/angular/commits/master))

```
docs(changelog): update changelog to beta.5
```

```
fix(release): need to depend on latest rxjs and zone.js

The version in our package.json gets copied to the one we publish, and users need the latest of these.
```

### Revert

If the commit reverts a previous commit, it should begin with `revert:`, followed by the header of the reverted commit. In the body it should say: `This reverts commit <hash>.`, where the hash is the SHA of the commit being reverted.

### Type

We follow semantic commit messages. [Check this website](https://seesparkbox.com/foundry/semantic_commit_messages) for more information.

Must be one of the following:

- **chore**: Changes to our project configuration files and scripts, such as CI or CD pipelines (example scopes: Travis, Circle)
- **docs**: Documentation only changes
- **feat**: A new feature
- **fix**: A bug fix
- **perf**: A code change that improves performance
- **refactor**: A code change that neither fixes a bug nor adds a feature
- **style**: Changes that do not affect the meaning of the code (white-space, formatting, missing semi-colons, etc)
- **test**: Adding missing tests or correcting existing tests

### Scope

The scope should be the name of the file affected (as perceived by the person reading the change-log generated from commit messages.)

Sample, where `Routes` is the scope:

```
feat(Routes): add new Main route skeleton
```

### Subject

The subject contains a succinct description of the change:

- use the imperative, present tense: "change" not "changed" nor "changes"
- don't capitalize the first letter
- no dot (.) at the end

### Body

Just as in the **subject**, use the imperative, present tense: "change" not "changed" nor "changes". The body should include the motivation for the change and contrast this with previous behaviour.

### Footer

The footer should contain any information about **Breaking Changes** and is also the place to reference GitLab issues that this commit **Closes**.

**Breaking Changes** should start with the word `BREAKING CHANGE:` with a space or two newlines. The rest of the commit message is then used for this.

### Full examples

```
feat(Service): add new ApiService helper class

ApiService helps to [insert justification here]
- Some detail of what this does
- More details

Closes #123
Breaks foo.baz api, foo.qux should be used instead
```

## Submission guidelines

### Submitting a Pull Request (PR)

The master branch is protected, and branch merging should be done solely through GitHub's merge requests.

Before you submit your Pull Request (PR) consider the following guidelines:

1. Make sure your changes are in a new git branch: \

```
git checkout -b my-fix-branch master
```

2. Create your patch, **including appropriate test cases** (cough).
3. Ensure all lints and tests pass (Do not disable the Git Hooks in this repository!).
4. Commit your changes using a descriptive commit message that follows our [commit message conventions](#commit).
5. Push your branch to GitHub: \

```
git push origin my-fix-branch
```

6. In GitHub, send a merge request to `main:master`
   - Code review will be performed by other members, and if changes are suggested, then:
     - Make the required updates.
     - Ensure linter and tests are still passing.
     - Rebase your branch and force push to your GitHub repository (this will update the Merge Request): \
     ```
     git rebase master -i &&
     git push -f
     ```
