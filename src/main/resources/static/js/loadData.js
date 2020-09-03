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
			element.querySelectorAll('*').forEach(n => n.remove());
			document.getElementById("pageNumbers1").querySelectorAll('*').forEach(n => n.remove());
			myJSON.forEach(ob => {
				element.appendChild(blockElement(ob));
			});

			$("#books").pagify(9, ".items");
		})
		.catch(function(error) {
		});

}

function search() {
	var requestOptions = {
		method: 'GET',
	};
	var keywords = document.getElementById("keywords").value;
	console.log(keywords);
	var aPromise = fetch("/search?keywords=" + keywords, requestOptions).then(function(response) {
		if (!response.ok) {
			throw new Error("HTTP error, status = " + response.status);
		}
		return response.json();
	})
		.then(function(myJSON) {
			document.getElementById("count").innerHTML = myJSON.length + "";
			var element = document.getElementById("books");
			element.querySelectorAll('*').forEach(n => n.remove());
			myJSON.forEach(ob => {
				element.appendChild(blockElement(ob));
			});
			document.getElementById("pageNumbers1").querySelectorAll('*').forEach(n => n.remove());
			$("#books").pagify(9, ".items");
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
	var li1 = document.createElement("li");
	element.appendChild(element1);
	return element;
}
