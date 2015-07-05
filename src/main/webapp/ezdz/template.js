$('input[type="file"]').ezdz();
var defaults = {
	className: '',
	text: 'Drop a file',
	previewImage:  true,
	value: null,
	classes: {
		main:  'ezdz-dropzone',
		enter: 'ezdz-enter',
		reject:'ezdz-reject',
		accept:'ezdz-accept'
	},
	validators: { // image validator
		maxSize:   null,
		width: null,
		maxWidth:  null,
		minWidth:  null,
		height:null,
		maxHeight: null,
		minHeight: null
	},
	init:   function() {},
	enter:  function() {},
	leave:  function() {},
	reject: function() {},
	accept: function() {},
	format: function(filename) {
		return filename;
	}
};