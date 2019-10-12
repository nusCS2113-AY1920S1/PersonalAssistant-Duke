# Contributing to Duchess

## Definitions

**Main Repository** Your group's repository.

**Fork** Your fork of your group's repository. 

## Setup

1.  Clone your fork to your computer.
```
git clone 'your fork address'
```

2.  Add the main repository as upstream.
```
git remote add upstream 'your main repo address'
```

## Basic Workflow

1.  Every work you do must address an open issue.

2.  Pull from upstream into your local repo's master.
```
git checkout master
git pull upstream master
```

3.  Create a branch from master and checkout to that branch.
```
git checkout -b your-branch-name
```

4.  Do your work. committing frequently with proper commit messages.
```
yes | sudo apt install sl && sl # Only if you're on Linux
```

5.  Pull from upstream into your branch and resolve merge conflicts if necessary.
```
git checkout your-branch-name
git fetch upstream master
git merge upstream/master
```

6.  Push your local branch to your fork.
```bash
git push origin your-branch-name
```

7.  Go to GitHub. Locate your branch in your fork and create a pull request from it to the main repository master.

8.  Label your pull request appropriately. 

9.  Add reviewers and wait for their review. Meanwhile, your code will be automatically tested for errors.

10. If your pull request is approved and passes the automated tests, squash and merge.
    Write proper messages.
    
11. Else, don't close your pull request. Instead, simply repeat steps 4 to 6.

## Advanced Workflow

If you are comfortable with the basic workflow, you can do the following things:

1.  In step 5, you can rebase instead of merging.

2.  In step 10, you can merge without squashing.