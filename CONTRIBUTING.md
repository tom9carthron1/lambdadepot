# How to Contribute to lambdadepot



## Did you find a bug?

We use [GitHub issues](https://github.com/homedepot/lambdadepot/issues) to track bugs and enhancements.

If you are reporting a bug, please help to speed up problem diagnosis by providing as much information as possible. Ideally, that would include a small sample project that reproduces the problem.



## Did you write a patch that fixes a bug?

Open a new [GitHub pull request](https://github.com/homedepot/lambdadepot/pulls) with the patch. Ensure the PR description clearly describes the problem and solution.



### Did you fix whitespace, format code, or make a purely cosmetic patch?

Changes that are cosmetic in nature and do not add anything substantial to the stability, functionality, or testability of _**lambdadepot**_ will generally not be accepted.



### Pull Request Checklist
1. Run the `test` gradle task
	* Verify no unit tests are failing

2. Run the `checkstyleMain` and `checkstyleTest` gradle tasks.
	* Verify there are no checkstyle errors for `main` and `test` source sets
	* Reports will be generated under the `build/reports/checkstyle` directory.

3. Run the `pitest` gradle task. 
	* Verify mutation test coverage is equal to or greater than existing mutation threshold
	* A report will be generated the under `build/reports/pitest` directory. 