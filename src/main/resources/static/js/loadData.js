function doGetJSON(id) {
	var url = "/cate/" + id;
	var aPromise = fetch(url);

	// Work with Promise object:
	aPromise
		.then(function(response) {
			if (!response.ok) {
				throw new Error("HTTP error, status = " + response.status);
			}
			return response.json();
		})
		.then(function(myJSON) {
			document.getElementById("count").innerHTML = myJSON.length + "";
			changeActive(id);
			var element = document.getElementById("books");
			var notfound = document.getElementById("notfound");
			if (notfound) {
				notfound.remove();
				element.querySelectorAll('*').forEach(n => n.remove())
			} else {
				element.querySelectorAll('*').forEach(n => n.remove());
			}

			if (myJSON.length > 0) {
				myJSON.forEach(ob => {
					element.appendChild(blockElement(ob));
				});
				$("#books").pagify(9, ".items");
			} else {
				element = document.getElementById("paginate");
				var basic = "<div id=\"books\" class=\"row\"></div><div class=\"pager product__pagination\"><div id=\"pageNumbers1\" class=\"pageNumbers\"></div></div>";
				element.innerHTML = basic + "<p class='alert alert-danger align-self-center' id='notfound'>Not Found</p>";
			}

		})
		.catch(function(error) {
			console.log("log: " + error);
		});

}

function search() {
	var keywords = document.getElementById("keywords").value;
	console.log(keywords);
	fetch("/search?keywords=" + keywords).then(function(response) {
		if (!response.ok) {
			throw new Error("HTTP error, status = " + response.status);
		}
		return response.json();
	})
		.then(function(myJSON) {
			document.getElementById("count").innerHTML = myJSON.length + "";
			changeActive(0);
			var element = document.getElementById("books");
			var notfound = document.getElementById("notfound");
			if (notfound) {
				notfound.remove();
				element.querySelectorAll('*').forEach(n => n.remove());
			} else {
				document.getElementsByClassName("product__pagination")[0].remove();
				element.querySelectorAll('*').forEach(n => n.remove());
			}
			if (myJSON.length > 0) {
				myJSON.forEach(ob => {
					element.appendChild(blockElement(ob));
				});

			} else {
				element = document.getElementById("paginate");
				var basic = "<div id=\"books\" class=\"row\"></div><div class=\"pager product__pagination\"><div id=\"pageNumbers1\" class=\"pageNumbers\"></div></div>";
				element.innerHTML = basic + "<p class='alert alert-danger align-self-center' id='notfound'>Not Found</p>";
			}

		})
		.catch(function(error) {
		});
}

function changeActive(id) {
	var x = document.getElementsByClassName("cate");
	for (let i = 0; i < 5; i++) {
		x[i].childNodes[0].style.color = "#1c1c1c";
		x[i].childNodes[0].style.fontWeight = "";

		if (id == i + 1) {
			console.log(x[i]);
			x[i].childNodes[0].style.color = "#0fa817";
			x[i].childNodes[0].style.fontWeight = "bold";
		}
	}
}

function blockElement(data) {
	var element = document.createElement("div");
	element.className = "col-lg-4 col-md-6 col-sm-6 items";
	var element1 = document.createElement("div");
	element1.className = "product__item";
	var bpic = document.createElement("div");
	bpic.className = "product__item__pic set-bg";
	bpic.setAttribute("data-setbg", '/img/book/' + data.id + '.jpg');
	bpic.setAttribute("style", "background-image: url(\"/img/book/" + data.id + ".jpg\");");
	element1.appendChild(bpic);
	var text = document.createElement("div");
	text.className = "product__item__text";
	var title = document.createElement("h6");
	var link = document.createElement("a");
	link.href = "/book?id=" + data.id;
	link.innerText = data.title;
	title.appendChild(link);
	text.appendChild(title);
	element1.appendChild(text);
	element.appendChild(element1);
	return element;
}
