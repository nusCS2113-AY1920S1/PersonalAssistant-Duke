# Contributing to Eggventory
Note: Pull requests are welcome. For making major changes, please create an issue to first discuss the changes and then add them. Please make sure you add appropriate test cases for your major commits to the repo. 

## Prerequisites
* Familiarize yourself with git commands
* Fork the group repository into your personal GitHub account
* Set up this project in IntellijIDEA
* Familiarize yourself with [gradle](https://github.com/AY1920S1-CS2113T-F11-1/main#tutorials) and [Travis CI](https://docs.travis-ci.com/user/tutorial/)
* Be well aware of the project [scope](https://nuscs2113-ay1920s1.github.io/website/admin/project-scope.html) and [constraints](https://nuscs2113-ay1920s1.github.io/website/admin/project-constraints.html)

## Definitions
Main repository: Our group's repository (AY1920S1-CS2113T-F11-1/main)

Your fork: Your fork of the group's repository

## Setup
Note: The steps provided here follow git CLI commands. If you are using Git GUIs like Source Tree, please make sure you follow steps that are equivalent to these commands.

1. Clone your fork into your computer.
```git
git clone 'your fork address'
```
2. Add the main repository as upstream
```git
git remote add upstream 'your main repo address'
```
## Workflow
Note: Dont push any commits directly to the the master of the main repository.

1. Your work must address an open issue.
2. Pull from upstream into your local repository's master.
```git
git checkout master
git pull upstream master
```
3. Create a branch from master and checkout to that branch
```git
git checkout -b your-branch-name
```
4. Add/delete/append the files that you want to. Remember to commit at regular intervals with proper [commit](https://chris.beams.io/posts/git-commit/) messages.
5. After finishing your work, merge your branch with upstream master.
```git
git checkout your-branch-name
git fetch upstream master
git merge upstream/master
```
6. Push your local branch to your fork
```git
git push origin your-branch-name
```
7. Go to GitHub. Locate your branch in your fork and create a pull request from it to the main repository master.
8. [Label](https://help.github.com/en/articles/applying-labels-to-issues-and-pull-requests) your pull request appropriately.
9. Your code would be automatically tested by Travis CI for errors.
10. Add reviewers and wait for their reviews.
11. If your pull request fails, dont close it. Just repeat steps 4 to 6.
12. Else if you are pull request is approved, squash and merge. Again, write proper messages for the same.
13. Tag your work done if required 
```git
git tag <extension>
```
14. Push the tags to main repository.
```git
git push upstream --tags
```

Adapted from Mohideen Imran Khan's [CONTRIBUTING.md](https://github.com/mohideenik/main/blob/master/CONTRIBUTING.md#contributing-to-duchess)
